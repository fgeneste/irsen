CREATE VIEW sennom as 
Select 
	substring("matricule", 5) as "SENMAT",
	civilite as "QUACOD",
	nom_usuel as "SENNOMUSE",
	UPPER(nom_usuel) as "SENNOMUSECAP",
	CONCAT( 
	replace(replace(trim(nom_usuel),' ', '_'),'-','_'),
	'_',
	replace(replace(trim(prenom_usuel),' ', '_'),'-','_')
	) as "SENNOMTEC",	
	prenom_usuel as "SENPRENOMUSE"
from etat_civil 
	inner join senateur on etat_civil.id=senateur.etat_civil_id
where senateur.traitee=false