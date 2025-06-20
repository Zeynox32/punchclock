package ch.axa.punchclock.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.axa.punchclock.models.Entry;
import jakarta.inject.Inject;

@SpringBootTest
public class EntryServiceTest {

    @Inject
    private Entryservice entryService;

    @Test
    public void testIfEntryCanBeSaved() {
        Entry entry = new Entry();
        entry.setDescription("Awesome");
        entry.setCheckIn(LocalDateTime.now());
        entry.setDuration(1234343);

        entryService.createEntry(entry);

        assertEquals(entry.getDescription(), entryService.readOneEntry(entry.getId()).getDescription());
    }

    @Test
    public void testIfEntryCanBeRead() {
        Entry entry = new Entry();
        entry.setDescription("Awesome");
        entry.setCheckIn(LocalDateTime.now());
        entry.setDuration(1234343);
        entryService.createEntry(entry);

        
        entryService.readOneEntry(entry.getId());

        assertEquals("Awesome", entryService.readOneEntry(entry.getId()).getDescription());
    }

    @Test
    public void testIfEntryCanBeUpdated() {
        Entry entry = new Entry();
        entry.setDescription("Awesome");
        entry.setCheckIn(LocalDateTime.now());
        entry.setDuration(1234343);
        entryService.createEntry(entry);

        entry.setDescription("Updated");
        entryService.updateEntry(entry);

        Entry updated = entryService.readOneEntry(entry.getId());
        assertEquals("Updated", updated.getDescription());
    }

   @Test
    public void testIfEntryCanBeDeleted() {
        Entry entry = new Entry();
        entry.setDescription("Awesome");
        entry.setCheckIn(LocalDateTime.now());
        entry.setDuration(1234343);

        entryService.createEntry(entry);
        Long id = entry.getId();

        entryService.deleteEntry(entry);

        assertNull(entryService.readOneEntry(id));
    }


   @Test
    public void testIfEntryCanBeListed() {
        Entry entry1 = new Entry();
        entry1.setDescription("Awesome");
        entry1.setCheckIn(LocalDateTime.now());
        entry1.setDuration(1234343);

        Entry entry2 = new Entry();
        entry2.setDescription("hfhsffdbgdf");
        entry2.setCheckIn(LocalDateTime.now());
        entry2.setDuration(1234343);

        entryService.createEntry(entry1);
        entryService.createEntry(entry2);

        List<Entry> entries = entryService.readAllEntries();
        assertTrue(entries.size() >= 2);
    }
}