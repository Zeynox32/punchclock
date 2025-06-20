package ch.axa.punchclock.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.axa.punchclock.models.Entry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class Entryservice {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void createEntry(Entry entry) {
        this.em.persist(entry);
    }

    public Entry readOneEntry(long id) {
        return this.em.find(Entry.class, id);
    }

    public List<Entry> readAllEntries(){
        return this.em.createQuery("SELECT e FROM Entry e", Entry.class).getResultList();
    }

    @Transactional
    public void updateEntry(Entry updatedEntry) {
        this.em.merge(updatedEntry);
    }

    @Transactional
    public void deleteEntry(Entry entry) {
        Entry managedEntry = em.contains(entry) ? entry : em.merge(entry);
        em.remove(managedEntry);
    }
}
