-- View: senateurs.vue_irene_mandat_actif

-- DROP VIEW senateurs.vue_irene_mandat_actif;

CREATE OR REPLACE VIEW senateurs.vue_irene_mandat_actif AS 
 SELECT sen.senprenomuse AS prenom,
    sen.sennomuse AS nom,
    t.senmat,
    t.eluid AS mandat_id,
    t.typmancod,
    t.titelecod AS fonction_cod,
    t.titelelib AS fonction_lib,
    t.eludatdeb AS mandat_date_debut,
    t.lieux AS mandat_lieu,
    t.elunbrhab AS nbr_hab,
        CASE
            WHEN t.elunbrhab IS NULL OR t.elunbrhab = 0 THEN 0
            ELSE 1
        END AS avec_population,
        CASE
            WHEN t.eludatdeb IS NULL THEN 0
            ELSE 1
        END AS avec_date_election
   FROM ( SELECT DISTINCT eluvil.senmat,
            eluvil.eluid,
            eluvil.typmancod,
            titele.titelecod,
            titele.titelelib,
            eluvil.eludatdeb,
            eluvil.evelib,
            eluvil.evelib AS lieux,
            eluvil.elunbrhab
           FROM senateurs.eluvil
             JOIN senateurs.elutit ON elutit.eluid = eluvil.eluid
             JOIN senateurs.titele ON elutit.titelecod::text = titele.titelecod::text
          WHERE eluvil.temvalcod::text = 'ACTIF'::text AND elutit.temvalcod::text = 'ACTIF'::text
        UNION
         SELECT DISTINCT elureg.senmat,
            elureg.eluid,
            elureg.typmancod,
            titele.titelecod,
            titele.titelelib,
            elureg.eludatdeb,
            elureg.evelib,
            reg.reglib AS lieux,
            elureg.elunbrhab
           FROM senateurs.elureg
             JOIN senateurs.elutit ON elutit.eluid = elureg.eluid
             JOIN senateurs.titele ON elutit.titelecod::text = titele.titelecod::text
             JOIN senateurs.reg ON elureg.regcod::text = reg.regcod::text
          WHERE elureg.temvalcod::text = 'ACTIF'::text AND elutit.temvalcod::text = 'ACTIF'::text
        UNION
         SELECT DISTINCT eluter.senmat,
            eluter.eluid,
            eluter.typmancod,
            titele.titelecod,
            titele.titelelib,
            eluter.eludatdeb,
            eluter.evelib,
            ''::text AS lieux,
            eluter.elunbrhab
           FROM senateurs.eluter
             JOIN senateurs.elutit ON elutit.eluid = eluter.eluid
             JOIN senateurs.titele ON elutit.titelecod::text = titele.titelecod::text
             JOIN senateurs.asster ON eluter.asstercod::text = asster.asstercod::text
          WHERE eluter.temvalcod::text = 'ACTIF'::text AND elutit.temvalcod::text = 'ACTIF'::text
        UNION
         SELECT DISTINCT elucan.senmat,
            elucan.eluid,
            elucan.typmancod,
            titele.titelecod,
            titele.titelelib,
            elucan.eludatdeb,
            elucan.evelib,
            dpt.dptcod::text AS lieux,
            elucan.elunbrhab
           FROM senateurs.elucan
             JOIN senateurs.elutit ON elutit.eluid = elucan.eluid
             JOIN senateurs.titele ON elutit.titelecod::text = titele.titelecod::text
             JOIN senateurs.dpt ON elucan.dptnum = dpt.dptnum
          WHERE elucan.temvalcod::text = 'ACTIF'::text AND elutit.temvalcod::text = 'ACTIF'::text
        UNION
         SELECT DISTINCT eludep.senmat,
            eludep.eluid,
            eludep.typmancod,
            titele.titelecod,
            titele.titelelib,
            eludep.eludatdeb,
            eludep.evelib,
            dpt.dptnum::text AS lieux,
            eludep.elunbrhab
           FROM senateurs.eludep
             JOIN senateurs.elutit ON elutit.eluid = eludep.eluid
             JOIN senateurs.titele ON elutit.titelecod::text = titele.titelecod::text
             JOIN senateurs.dpt ON eludep.depcod::text = dpt.dptcod::text
          WHERE eludep.temvalcod::text = 'ACTIF'::text AND elutit.temvalcod::text = 'ACTIF'::text
        UNION
         SELECT DISTINCT elueur.senmat,
            elueur.eluid,
            elueur.typmancod,
            titele.titelecod,
            titele.titelelib,
            elueur.eludatdeb,
            elueur.evelib,
            ''::text AS lieux,
            elueur.elunbrhab
           FROM senateurs.elueur
             JOIN senateurs.elutit ON elutit.eluid = elueur.eluid
             JOIN senateurs.titele ON elutit.titelecod::text = titele.titelecod::text
          WHERE elueur.temvalcod::text = 'ACTIF'::text AND elutit.temvalcod::text = 'ACTIF'::text
        UNION
         SELECT DISTINCT elumet.senmat,
            elumet.eluid,
            'MET'::text AS typmancod,
            titele.titelecod,
            titele.titelelib,
            elumet.eludatdeb,
            ''::text AS evelib,
            met.metlib AS lieux,
            0 AS elunbrhab
           FROM senateurs.elumet
             JOIN senateurs.elutit ON elutit.eluid = elumet.eluid
             JOIN senateurs.titele ON elutit.titelecod::text = titele.titelecod::text
             JOIN senateurs.met ON met.metcod::text = elumet.metcod::text
          WHERE elumet.eludatfin IS NULL
        UNION
         SELECT DISTINCT minind.senmat,
            minind.minid AS eluid,
            'GOUV'::text AS typmancod,
            titmin.titmincod AS titelecod,
            titmin.titminlib AS titelelib,
            minind.mindatdeb AS eludatdeb,
            ''::text AS evelib,
            minind.evelib AS lieux,
            0 AS elunbrhab
           FROM senateurs.minind
             JOIN senateurs.titmin ON minind.titmincod::text = titmin.titmincod::text
          WHERE minind.temvalcod::text = 'ACTIF'::text) t
     JOIN senateurs.sen ON t.senmat = sen.senmat
     JOIN senateurs.elusen ON sen.senmat = elusen.senmat
  WHERE elusen.temvalcod::text = 'ACTIF'::text;

ALTER TABLE senateurs.vue_irene_mandat_actif
  OWNER TO pgdba;
