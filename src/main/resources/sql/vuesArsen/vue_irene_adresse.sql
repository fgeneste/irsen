-- View: senateurs.vue_irene_adresse

-- DROP VIEW senateurs.vue_irene_adresse;

CREATE OR REPLACE VIEW senateurs.vue_irene_adresse AS 
 SELECT sen.senmat AS matricule,
    poicon.typpoiconcod AS type,
    poicon.poiconid AS adresse_id,
    adresse.typbistercod AS adresse_bister,
    adresse.typvoicod AS adresse_typvoiecod,
    typvoi.typvoilib AS adresse_typvoie,
    adresse.adrnumvoi AS adresse_numvoie,
    adresse.adrnomvoi AS adresse_nomvoie,
    adresse.adrcom AS adresse_commune,
    adresse.adrcodpos AS adresse_cp,
    adresse.evetempub AS adresse_notice,
    adresse.evetempubintra AS adresse_notice_intra,
    telephone.typtelcod AS tel_typcod,
    typtel.typtellib AS tel_typ,
    telephone.telnum AS tel,
    telephone.evetempub AS tel_notice,
    telephone.evetempubintra AS tel_notice_intra,
    mel.melema AS mel,
    mel.evetempub AS mel_notice,
    mel.evetempubintra AS mel_notice_intra
   FROM senateurs.sen
     JOIN senateurs.elusen ON sen.senmat = elusen.senmat
     JOIN senateurs.poicon ON sen.senmat = poicon.senmat
     LEFT JOIN senateurs.adresse ON adresse.poiconid = poicon.poiconid
     LEFT JOIN senateurs.typvoi ON adresse.typvoicod::text = typvoi.typvoicod::text
     LEFT JOIN senateurs.telephone ON telephone.poiconid = poicon.poiconid
     LEFT JOIN senateurs.typtel ON telephone.typtelcod::text = typtel.typtelcod::text
     LEFT JOIN senateurs.mel ON mel.poiconid = poicon.poiconid
  WHERE elusen.temvalcod::text = 'ACTIF'::text;

ALTER TABLE senateurs.vue_irene_adresse
  OWNER TO pgdba;
