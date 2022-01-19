-- View: senateurs.vue_irene_etat_civil

-- DROP VIEW senateurs.vue_irene_etat_civil;

CREATE OR REPLACE VIEW senateurs.vue_irene_etat_civil AS 
 SELECT sen.senmat AS matricule,
    sen.quacod AS civilite,
    sen.senfem AS feminisation,
    sen.sennompat AS nom_famille,
    sen.sennommar AS nom_marital,
    sen.senprenomciv AS prenoms,
    sen.sennomuse AS nom_usuel,
    sen.senprenomuse AS prenom_usuel,
    sen.sendatnai AS date_naissance,
    dpt.dptcod AS departement_naissance,
    sen.senlienai AS commune_naissance,
    sen.sendespro AS profession,
    pcs.pcs42cod AS pcs,
    sen.senema AS courriel,
    sen.sendecora AS decorations,
    sen.senautema AS diffusion_email,
    sen.senautpagper AS diffusion_pages,
    sen.senauttra AS diffusion_travaux,
    sen.senautgrpsen AS diffusion_groupe_sen
   FROM senateurs.sen
     JOIN senateurs.elusen ON sen.senmat = elusen.senmat
     LEFT JOIN senateurs.pcs ON sen.pcscod = pcs.pcscod
     LEFT JOIN senateurs.dpt ON sen.sendptnumnai = dpt.dptnum
  WHERE elusen.temvalcod::text = 'ACTIF'::text;

ALTER TABLE senateurs.vue_irene_etat_civil
  OWNER TO pgdba;
