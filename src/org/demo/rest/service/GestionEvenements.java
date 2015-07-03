package org.demo.rest.service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.demo.rest.bean.Evenement;
import org.demo.rest.bean.Participant;

import redis.clients.jedis.Jedis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/evenements")
public class GestionEvenements {
	@POST
	@Path("/{id}/inscription")
	@Consumes(MediaType.APPLICATION_JSON)
	public String inscription(Participant p, final @PathParam("id") String name) throws JsonProcessingException {

		//		Jedis jedis = new Jedis("localhost");
		//		String value = jedis.get(name);
		//		jedis.close();
		//
		//		Evenement e = new Evenement();
		//		e.setAdresse("2 rue de la liberation");
		//		e.setNom("Evt1");
		//		e.setDateDebut(new Date());
		//		e.setDateFin(new Date());
		//
		Participant pa = new Participant();
		pa.setEmail("toto@capgemini.com");
		pa.setNom("toto");
		pa.setPrenom("tutu");
		pa.setSociete("Capgemini");
		ObjectMapper om = new ObjectMapper();

		return om.writeValueAsString(p);

		//		Jedis jedis = new Jedis("localhost");
		//		String value = jedis.get(e.getNom());
		//		jedis.close();
	}

	/**
	 * Création Evenevement.
	 * @param e Evenement
	 * @return 
	 * @throws JsonProcessingException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createEvenement(Evenement e) throws JsonProcessingException {

		ObjectMapper om = new ObjectMapper();
		String result = "KO";

		Jedis jedis = new Jedis("localhost");

		String value = jedis.get(e.getNom());
		if (value == null) {
			jedis.set(e.getNom(), om.writeValueAsString(e));
			jedis.close();
			result = "OK";
		} else {
			result = "existe déjà !";
		}

		return Response.status(201).entity(result).build();
	}

	/**
	 * Modifier Evenevement.
	 * @param e Evenement
	 * @return 
	 * @throws JsonProcessingException
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modifyEvenement(Evenement e, final @PathParam("id") String name) throws JsonProcessingException {

		ObjectMapper om = new ObjectMapper();
		String result = "KO";

		Jedis jedis = new Jedis("localhost");

		String value = jedis.get(name);
		if (value != null) {
			jedis.set(name, om.writeValueAsString(e));
			jedis.close();
			result = "OK";
		} else {
			result = "existe pas ! => utiliser POST svp";
		}

		return Response.status(201).entity(result).build();
	}

	/**
	 * Publier Evenevement.
	 * @param e Evenement
	 * @return 
	 * @throws IOException 
	 */
	@PUT
	@Path("/{id}/publish")
	public Response publishEvenement(final @PathParam("id") String name) throws IOException {

		ObjectMapper om = new ObjectMapper();
		String result = "KO";

		Jedis jedis = new Jedis("localhost");

		String value = jedis.get(name);
		if (value != null) {
			Evenement f = om.readValue(value, Evenement.class);
			f.setPublish(true);
			jedis.set(f.getNom(), om.writeValueAsString(f));
			jedis.close();
			result = "URI : http://localhost:8080/jax-rs/rest/evenements/" + f.getNom() + "/inscription";
		} else {
			result = "existe pas !";
		}

		return Response.status(201).entity(result).build();
	}
}
