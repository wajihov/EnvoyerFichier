<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>INDEX</title>
</head>
<body>
	<c:if test="${!empty fichier }">
		<p>
			<c:out
				value="le fichier ${fichier } (${description }) a été téléchargé"></c:out>
		</p>
	</c:if>
	<form action="index" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>Description :</td>
				<td><input type="text" name="description"></td>
			</tr>
			<tr>
				<td>Fichier a envoyé :</td>
				<td><input type="file" name="fichier"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Envoyer"></td>
			</tr>
		</table>
	</form>
</body>
</html>