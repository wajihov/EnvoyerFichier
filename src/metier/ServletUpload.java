package metier;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/ServletUpload")
public class ServletUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final int taille_tampon = 10240;
	public static final String chemin_fichier = "C://AMD//Telechargement//";

	public ServletUpload() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// this.getServletContext().getRequestDispatcher("/bonjour.jsp").forward(request,
		// response);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String description = request.getParameter("description");
		request.setAttribute("description", description);

		Part part = request.getPart("fichier");
		// on verifier qu'on a bien reçu le fichier
		String nomFichier = getNomFichier(part);

		// si on a bien le fichier
		if (nomFichier != null && !nomFichier.isEmpty()) {
			String nomChamp = part.getName();
			// corriger un bug d'internet expolorer
			nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
					.substring(nomFichier.lastIndexOf('\\') + 1);
			// on ecrit le chemin sur le disque
			ecrireFichier(part, nomFichier, chemin_fichier);
			request.setAttribute(nomChamp, nomFichier);
		}
		this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}

	private void ecrireFichier(Part part, String nomFichier, String cheminFichier) throws IOException {
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		try {
			entree = new BufferedInputStream(part.getInputStream(), taille_tampon);
			sortie = new BufferedOutputStream(new FileOutputStream(cheminFichier + nomFichier), taille_tampon);

			byte[] tampon = new byte[taille_tampon];
			int longueur;
			while ((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}

		} finally {
			try {
				sortie.close();
			} catch (Exception e) {

			}
			try {
				entree.close();
			} catch (Exception e) {

			}

		}
	}

	private static String getNomFichier(Part part) {
		for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
			if (contentDisposition.trim().startsWith("filename")) {
				return contentDisposition.substring(contentDisposition.indexOf('0') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

}
