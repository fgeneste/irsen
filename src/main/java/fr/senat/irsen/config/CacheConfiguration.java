package fr.senat.irsen.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, fr.senat.irsen.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, fr.senat.irsen.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, fr.senat.irsen.domain.User.class.getName());
            createCache(cm, fr.senat.irsen.domain.Authority.class.getName());
            createCache(cm, fr.senat.irsen.domain.User.class.getName() + ".authorities");
            createCache(cm, fr.senat.irsen.domain.Senateur.class.getName());
            createCache(cm, fr.senat.irsen.domain.DepartementNaissance.class.getName());
            createCache(cm, fr.senat.irsen.domain.PaysNaissance.class.getName());
            createCache(cm, fr.senat.irsen.domain.CategorieSocioProf.class.getName());
            createCache(cm, fr.senat.irsen.domain.EtatCivil.class.getName());
            createCache(cm, fr.senat.irsen.domain.AdresseFiscale.class.getName());
            createCache(cm, fr.senat.irsen.domain.AdressePostale.class.getName());
            createCache(cm, fr.senat.irsen.domain.AdressePostale2.class.getName());
            createCache(cm, fr.senat.irsen.domain.Adresses.class.getName());
            createCache(cm, fr.senat.irsen.domain.FonctionAncien.class.getName());
            createCache(cm, fr.senat.irsen.domain.FonctionAncien.class.getName() + ".fonctions");
            createCache(cm, fr.senat.irsen.domain.FonctionEnCours.class.getName());
            createCache(cm, fr.senat.irsen.domain.FonctionEnCours.class.getName() + ".fonctions");
            createCache(cm, fr.senat.irsen.domain.Mandat.class.getName());
            createCache(cm, fr.senat.irsen.domain.MandatAncien.class.getName());
            createCache(cm, fr.senat.irsen.domain.MandatAncien.class.getName() + ".anciensMandats");
            createCache(cm, fr.senat.irsen.domain.MandatEnCours.class.getName());
            createCache(cm, fr.senat.irsen.domain.MandatEnCours.class.getName() + ".mandatsEnCours");
            createCache(cm, fr.senat.irsen.domain.Mandat.class.getName() + ".mandatAnciens");
            createCache(cm, fr.senat.irsen.domain.Mandat.class.getName() + ".mandatEnCours");
            createCache(cm, fr.senat.irsen.domain.MandatAncien.class.getName() + ".fonctionAnciens");
            createCache(cm, fr.senat.irsen.domain.MandatEnCours.class.getName() + ".fonctionEnCours");
            createCache(cm, fr.senat.irsen.domain.Mandat.class.getName() + ".anciensMandats");
            createCache(cm, fr.senat.irsen.domain.Mandat.class.getName() + ".mandatsEnCours");
            createCache(cm, fr.senat.irsen.domain.MandatAncien.class.getName() + ".fonctions");
            createCache(cm, fr.senat.irsen.domain.MandatEnCours.class.getName() + ".fonctions");
            createCache(cm, fr.senat.irsen.domain.Senateur.class.getName() + ".decorations");
            createCache(cm, fr.senat.irsen.domain.Decoration.class.getName());
            createCache(cm, fr.senat.irsen.domain.TelephonePortable.class.getName());
            createCache(cm, fr.senat.irsen.domain.TelephonePortable2.class.getName());
            createCache(cm, fr.senat.irsen.domain.TelephoneFixe.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
