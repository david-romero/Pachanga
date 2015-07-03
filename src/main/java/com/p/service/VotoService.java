package com.p.service;



import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.p.model.User;
import com.p.model.Voto;
import com.p.model.repositories.VotoRepository;

@Service
public class VotoService {
	
	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
	private VotoRepository repository;
	

	@Transactional()
	public Voto votar(Voto voto, User emisor, User receptor) {
		//Debemos consultar que no exista ya un voto entre el emisor y el receptor
		Assert.isTrue(getVotoBetween(emisor, receptor) == null);
		voto.setEmisor(emisor);
		voto.setReceptor(receptor);
		voto.setFecha(new Date(System.currentTimeMillis()));
		notificacionService.notificarVoto(voto);
		return save(voto);
	}
	
	@Transactional
	private Voto save(Voto voto) {
		return repository.save(voto);
	}

	@Transactional(readOnly=true)
	public Voto getVotoBetween(User emisor, User receptor){
		return repository.getVotoBetween(receptor.getId(), emisor.getId());
	}
	@Transactional(readOnly=true)
	public Collection<? extends Voto> findAll(User usrReceptor) {
		return repository.getAllVotesRecibidos(usrReceptor.getId());
	}
	@Transactional(readOnly=true)
	public Integer calcularMedia(User usrReceptor) {
		List<Voto> votosUsuario = Lists.newArrayList();
		votosUsuario.addAll(findAll(usrReceptor));
		List<Double> votosValoresUsuario2 = votosUsuario.parallelStream().map(voto->{return new Double( voto.getValor());}).collect(Collectors.toList());
		double sum2 = votosValoresUsuario2
	            .parallelStream()
	            .mapToDouble(Double::doubleValue)
	            .sum();
		Integer numeroMaximoVotosPosiblesObtener = votosUsuario.size() * 5;
		return (int) (sum2 * 5 / (numeroMaximoVotosPosiblesObtener));
	}
	@Transactional(readOnly=true)
	public Map<Integer, Integer> getVotos(User usrReceptor) {
		List<Voto> votosUsuario = Lists.newArrayList();
		votosUsuario.addAll(findAll(usrReceptor));
		Map<Integer, Integer> mapa = Maps.newHashMap();
		mapa.put(0, votosUsuario.size());
		for ( Voto voto : votosUsuario ){
			Integer conteoVotos = 1;
			if ( mapa.containsKey(voto.getValor()) ){
				conteoVotos = mapa.get(voto.getValor()) + 1;
			}
			mapa.put(voto.getValor(), conteoVotos);
		}
		for (  int i = 1; i <= 5; i++ ){
			if ( !mapa.containsKey(i) ){
				mapa.put(i, 0);
			}
		}
		return mapa;
	}

}
