CREATE VIEW sen as 
Select 
	substring("matricule", 5) as "SENMAT",
	civilite as "QUACOD",
	nom_usuel as "SENNOMUSE",
	nom_famille as "SENNOMPAT",
	nom_marital as "SENNOMMAR",	
	CONCAT( 
	replace(replace(trim(nom_usuel),' ', '_'),'-','_'),
	'_',
	replace(replace(trim(prenom_usuel),' ', '_'),'-','_')
	) as "SENNOMTEC",
	prenom_usuel as "SENPRENOMUSE",
	prenoms as "SENPRENOMCIV",
	date_naissance as "SENDATNAI",
	commune_naissance as "SENLIENAI",
	profession as "SENDESPRO",
	categorie_socio_prof.code as "PCSCOD42_TO_TRANSLATE",
	categorie_socio_prof.code as "CATPROCOD_TO_TRANSLATE",
	courriel as "SENEMA", 
	'' as "SENNUMTELSEN",
	UPPER(nom_usuel) as "SENNOMUSECAP",
	departement_naissance.code as "SENDPTNUMNAI_TO_TRANSLATE",
	decora.decora as "SENDECORA"
from etat_civil 
	inner join departement_naissance on etat_civil.departement_naissance_id=departement_naissance.id
	inner join pays_naissance on etat_civil.pays_naissance_id=pays_naissance.id	
	inner join categorie_socio_prof on etat_civil.categorie_socio_prof_id=categorie_socio_prof.id
	inner join senateur on etat_civil.id=senateur.etat_civil_id
	inner join decora on senateur.id=decora.senateur_id
where senateur.traitee=false