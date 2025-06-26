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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import ch.axa.punchclock.models.Tags;
import ch.axa.punchclock.repositories.TagRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tags")
@Tag(name = "Tag", description = "Verwaltet Tags")
public class APITagController {

  @Autowired
  private TagRepository tagRepository;

  @Operation(summary = "Erstellt einen neuen Tag")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Tags create(@RequestBody @Valid Tags tag) {
    return tagRepository.save(tag);
  }

  @Operation(summary = "Listet alle Tags auf")
  @GetMapping
  public Iterable<Tags> index() {
    return tagRepository.findAll();
  }

  @Operation(summary = "Gibt einen bestimmten Tag anhand der ID zurück")
  @GetMapping("/{id}")
  public ResponseEntity<Tags> read(
    @Parameter(description = "ID des Tags", required = true)
    @PathVariable long id) {
    return ResponseEntity.of(tagRepository.findById(id));
  }

  @Operation(summary = "Aktualisiert einen bestehenden Tag")
  @PutMapping("/{id}")
  public Tags update(
    @Parameter(description = "ID des Tags", required = true)
    @PathVariable long id,
    @RequestBody @Valid Tags tag) {
    tag.setId(id);
    return tagRepository.save(tag);
  }

  @Operation(summary = "Löscht einen Tag anhand der ID")
  @DeleteMapping("/{id}")
  public ResponseEntity<Tags> delete(
    @Parameter(description = "ID des zu löschenden Tags", required = true)
    @PathVariable long id) {
    var entry = tagRepository.findById(id);
    if(entry.isPresent()) {
      tagRepository.delete(entry.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}