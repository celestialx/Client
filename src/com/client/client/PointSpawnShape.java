package com.client.client;

import java.util.Random;

import com.client.client.particles.ParticleVector;

public class PointSpawnShape implements SpawnShape {

	private ParticleVector vector;

	public PointSpawnShape(ParticleVector vector) {
		this.vector = vector;
	}

	@Override
	public final ParticleVector divide(Random random) {
		return vector.clone();
	}
}