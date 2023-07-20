package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(name="BlogUser.upit.nick",query="select u from BlogUser as u where u.nick=:nick")
})
@Entity
@Table(name = "blog_users")
public class BlogUser {
    private Long id;
    private String firstName, lastName, nick, email, passwordHash;
    private List<BlogEntry> blogEntries = new ArrayList<>();

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @Column(length = 100, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    @Column(length = 100, nullable = false)
    public String getLastName() {
        return lastName;
    }

    @Column(length = 100, nullable = false, unique = true)
    public String getNick() {
        return nick;
    }

    @Column(length = 100, nullable = false)
    public String getEmail() {
        return email;
    }

    @Column(length = 64, nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<BlogEntry> getBlogEntries() {
        return blogEntries;
    }

    public void setBlogEntries(List<BlogEntry> blogEntries) {
        this.blogEntries = blogEntries;
    }

}
