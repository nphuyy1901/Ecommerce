package cybersoft.javabackend.ecommerce.user.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import cybersoft.javabackend.ecommerce.role.model.Role;
import cybersoft.javabackend.ecommerce.user.model.User;


@Service
public class CustomUserDetailService implements UserDetailsService{
	 	@Autowired
	    UserService userService;

	    @Override
	    @Transactional
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	    	  Optional<User> user = userService.findByUsername(username);
	          List<GrantedAuthority> authorities = getUserAuthority(user.get().getRoles());
	          return buildUserForAuthentication(user.get(), authorities);
	    }

	    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
	        Set<GrantedAuthority> roles = new HashSet<>();
	        for (Role role : userRoles) {
	            roles.add(new SimpleGrantedAuthority(role.getName()));
	        }
	        return new ArrayList<>(roles);
	    }

	    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
	        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
	                user.getActivated(), true, true, true, authorities);
	    }
}
