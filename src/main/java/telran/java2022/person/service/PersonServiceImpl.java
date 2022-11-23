package telran.java2022.person.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java2022.person.dao.PersonRepository;
import telran.java2022.person.dto.AddressDto;
import telran.java2022.person.dto.CityPopulationDto;
import telran.java2022.person.dto.PersonDto;
import telran.java2022.person.dto.PersonNotFoundException;
import telran.java2022.person.model.Person;
import telran.java2022.person.model.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
	
	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Override
	public Boolean addPerson(PersonDto personDto) {
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto removePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.deleteById(id);
		return modelMapper.map(person,PersonDto.class);
	}

	@Override
	public PersonDto updatePersonName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if(name != null)
			person.setName(name);
		personRepository.save(person);
		return modelMapper.map(person,PersonDto.class);
	}

	@Override
	public PersonDto updatePersonAddress(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if(addressDto.getBuilding() != null)
			person.getAddress().setBuilding(addressDto.getBuilding());
		if(addressDto.getCity() != null)
			person.getAddress().setCity(addressDto.getCity());
		if(addressDto.getStreet() != null)
			person.getAddress().setStreet(addressDto.getStreet());
		personRepository.save(person);
		return modelMapper.map(person,PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findPersonsByCity(String city) {
		Iterable<Person> persons = new ArrayList<>();
		if(city != null)
			persons =personRepository.findAllByAddress_City(city);

		return StreamSupport.stream(persons.spliterator(),false)
				.map(person -> modelMapper.map(person,PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<PersonDto> findPersonsByName(String name) {
		Iterable<Person> persons = new ArrayList<>();
		if(name != null)
			persons =personRepository.findAllByName(name);

		return StreamSupport.stream(persons.spliterator(),false)
				.map(person -> modelMapper.map(person,PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<PersonDto> findPersonsBetweenAge(Integer minAge, Integer maxAge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<CityPopulationDto> getCitiesPopulation() {
		// TODO Auto-generated method stub
		return null;
	}

}
