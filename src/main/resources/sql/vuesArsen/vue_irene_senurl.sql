-- View: senateurs.vue_irene_senurl

-- DROP VIEW senateurs.vue_irene_senurl;

CREATE OR REPLACE VIEW senateurs.vue_irene_senurl AS 
 SELECT senurl.senmat AS matricule,
    senurl.typurlcod AS type_url,
    senurl.senurlurl AS url,
    senurl.evetempub AS public
   FROM senateurs.senurl
     JOIN senateurs.elusen ON senurl.senmat = elusen.senmat
  WHERE elusen.temvalcod::text = 'ACTIF'::text;

ALTER TABLE senateurs.vue_irene_senurl
  OWNER TO pgdba;
