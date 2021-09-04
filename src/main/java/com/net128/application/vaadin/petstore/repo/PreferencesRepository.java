package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
    default Preferences saveOrUpdate(Preferences preferences) {
        if(preferences.getId() != null) {
            var persistedPreferences = findById(preferences.getId());
            if(persistedPreferences.isPresent()) {
                persistedPreferences.get().copy(preferences);
                preferences = persistedPreferences.get();
            }
            return save(preferences);
        }
        return null;
    }
}
