package de.helfenkannjeder.common.identityprovider.rest;

import de.helfenkannjeder.common.identityprovider.rest.dto.UserDto;
import de.helfenkannjeder.common.identityprovider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("users/")
@Transactional
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "current", method = RequestMethod.GET)
	public UserDto user() {
		return null;
	}
}
