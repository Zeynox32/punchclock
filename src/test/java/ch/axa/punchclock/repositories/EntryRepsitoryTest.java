package ch.axa.punchclock.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.axa.punchclock.models.Entry;
import jakarta.inject.Inject;

@SpringBootTest
public class EntryRepsitoryTest {

    @Inject
    private EntryRepository er;
    
    @Test
    public void testIfEntryCanBeSaved() {
        Entry entry = new Entry();
        entry.setDescription("Awesome");
        entry.setCheckIn(LocalDateTime.now());
        entry.setDuration(1234343);

        er.save(entry);

        assertEquals(entry.getDescription(), er.findById(entry.getId()).get().getDescription());
    }

    @Test
    public void testIfEntryCanBeUpdated() {
        Entry entry = new Entry();
        entry.setDescription("Awesome");
        entry.setCheckIn(LocalDateTime.now());
        entry.setDuration(1234343);
        er.save(entry);

        entry.setDescription("Updated");
        er.save(entry);

        Entry updated = er.findById(entry.getId()).get();
        assertEquals("Updated", updated.getDescription());
    }

    @Test
    public void testIfEntryCanBeListed() {
        Entry entry1 = new Entry();
        entry1.setDescription("Awesome");
        entry1.setCheckIn(LocalDateTime.now());
        entry1.setDuration(1234343);

        Entry entry2 = new Entry();
        entry2.setDescription("Awesome");
        entry2.setCheckIn(LocalDateTime.now());
        entry2.setDuration(1234343);

        er.save(entry1);
        er.save(entry2);

        List<Entry> result = new ArrayList<Entry>();
        er.findAll().forEach(result::add);
        assertTrue(result.size() >= 2);
    }

    @Test
    public void testIfEntryCanBeDeleted() {
        Entry entry = new Entry();
        entry.setDescription("Awesome");
        entry.setCheckIn(LocalDateTime.now());
        entry.setDuration(1234343);

        er.save(entry);
        Long id = entry.getId();

        er.delete(entry);

        assertTrue(er.findById(id).isEmpty());
    }
}
