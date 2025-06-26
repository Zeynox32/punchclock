package ch.axa.punchclock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.axa.punchclock.models.Category;
import ch.axa.punchclock.repositories.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "Verwaltet Kategorien")
public class APICategoryController {

  @Autowired
  private CategoryRepository categoryRepository;
    
  @Operation(summary = "Erstellt eine neue Kategorie", description = "Nimmt ein gültiges Kategorieobjekt entgegen und speichert es in der Datenbank.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Category create(@RequestBody @Valid Category category) {
    return categoryRepository.save(category);
  }

  @Operation(summary = "Listet alle Kategorien auf")
  @GetMapping
  public Iterable<Category> index() {
    return categoryRepository.findAll();
  }

  @Operation(summary = "Gibt eine bestimmte Kategorie anhand der ID zurück")
  @GetMapping("/{id}")
  public ResponseEntity<Category> read(
    @Parameter(description = "ID der gesuchten Kategorie", required = true)
    @PathVariable long id) {
    return ResponseEntity.of(categoryRepository.findById(id));
  }

  @Operation(summary = "Aktualisiert eine bestehende Kategorie anhand der ID")
  @PutMapping("/{id}")
  public Category update(
    @Parameter(description = "ID der zu aktualisierenden Kategorie", required = true)
    @PathVariable long id,
    @RequestBody @Valid Category category) {
    category.setId(id);
    return categoryRepository.save(category);
  }

  @Operation(summary = "Löscht eine bestimmte Kategorie")
  @DeleteMapping("/{id}")
  public ResponseEntity<Category> delete(
    @Parameter(description = "ID der zu löschenden Kategorie", required = true)
    @PathVariable long id) {
    var category = categoryRepository.findById(id);
    if(category.isPresent()) {
      categoryRepository.delete(category.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
