package cybersoft.javabackend.ecommerce.role.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cybersoft.javabackend.ecommerce.role.dto.RoleDTO;
import cybersoft.javabackend.ecommerce.role.dto.UpdateRoleDTO;
import cybersoft.javabackend.ecommerce.role.exception.InvalidRoleException;
import cybersoft.javabackend.ecommerce.role.model.Role;
import cybersoft.javabackend.ecommerce.role.repository.RoleRepository;
import cybersoft.javabackend.ecommerce.role.util.RoleConverter;

@Service
public class RoleServiceImpl implements RoleService {
	private RoleRepository repository;
	
	public RoleServiceImpl(RoleRepository roleRepository) {
		repository = roleRepository;
	}

	@Override
	public List<RoleDTO> findAllDTO() {
		List<Role> roles = repository.findAll();
		
		if (roles.isEmpty()) {
			//throw new NotFoundException("This is for test only!.");
			return null;
		}
		
		return RoleConverter.toRoleDTOs(roles);
	}

	public RoleDTO create(RoleDTO dto) {
		Role role = RoleConverter.toRole(dto);
		
		Role createdRole = repository.save(role);
		
		return RoleConverter.toRoleDTO(createdRole);
	}

	@Override
	public Optional<Role> findByName(String roleName) {
		return repository.findByName(roleName);
	}

	@Override
	public RoleDTO updateRole(long id, UpdateRoleDTO dto) {
		Optional<Role> roleOpt = repository.findById(id);

		if (!roleOpt.isPresent()) {
			throw new InvalidRoleException("Role id is not valid");
		}
		
		Role role = roleOpt.get();
		
		if (!role.getName().equals(dto.getName())) {
			if (repository.findByName(dto.getName()).isPresent()) {
				throw new InvalidRoleException("Role name has been used.");
			}
			
			role.setName(dto.getName());
		}
		
		if (!role.getCode().equals(dto.getCode())) {
			if (repository.findByCode(dto.getCode()).isPresent()) {
				throw new InvalidRoleException("Role code has been used.");
			}
			
			role.setCode(dto.getCode());
		}
		
		role.setDescription(dto.getDescription());
		
		Role updatedRole = repository.save(role);
		
		return RoleConverter.toRoleDTO(updatedRole);
	}

	@Override
	public void deleteRole(long id) {
		Optional<Role> roleOpt = repository.findById(id);
		
		if (!roleOpt.isPresent())
			throw new InvalidRoleException("Role ID is not existed.");
		
		repository.delete(roleOpt.get());
	}
	
}
