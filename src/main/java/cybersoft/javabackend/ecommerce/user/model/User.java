package cybersoft.javabackend.ecommerce.user.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import cybersoft.javabackend.ecommerce.role.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="username", nullable = false, unique = true)
	private String username;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="email", nullable = false, unique = true)
	private String email;
	
	@Column(name="first_name", nullable = false)
	private String first_name;
	
	@Column(name="last_name", nullable = false)
	private String last_name;
	
	@Column(name="phone", nullable = false, unique = true)
	private String phone;
	
	@Column(name="activated")
	private Boolean activated;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
			name = "role_user",
			joinColumns= {@JoinColumn(name="user_id")},
			inverseJoinColumns = {@JoinColumn(name="role_id")}
			)
	private Set<Role> roles = new HashSet<>();

	

	

	
}

