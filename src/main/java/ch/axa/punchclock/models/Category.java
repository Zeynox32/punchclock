package ch.axa.punchclock.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 255)
    @NotBlank(message = "Darf nicht leer sein!")
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Entry> entries = new HashSet<>();

     public long getId() {
         return id;
     }

     public void setId(long id) {
         this.id = id;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public Set<Entry> getEntries() {
         return entries;
     }

     public void setEntries(Set<Entry> entries) {
         this.entries = entries;
     }

}
