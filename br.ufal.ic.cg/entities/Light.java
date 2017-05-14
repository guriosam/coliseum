package entities;

import com.jogamp.opengl.GL2;

public class Light {
	
	public void init_lighting(GL2 gl) {

		float luzAmbiente[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		float luzDifusa[] = { 0.7f, 0.7f, 0.7f, 1.0f }; // "cor"
		float luzEspecular[] = { 1.0f, 1.0f, 1.0f, 1.0f };// "brilho"
		
		float posicaoLuz[] = { 2.5f, 2.5f, 5.5f, 1.0f };
		// float posicaoLuz2[] = { -3.5f, 2f, 5.2f, 1.0f };
		float posicaoLuz3[] = { -3.5f, 2f, 3.2f, 1.0f };
		float especularidade[] = { 1, 1, 1, 1 };
		int especMaterial = 60;

		// Habilita o uso de iluminação
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		// Define a refletância do material
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, especularidade, 1);
		// Define a concentração do brilho
		gl.glMateriali(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, especMaterial);
		// gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, GL2.GL_TRUE);

		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, posicaoLuz, 3);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, luzAmbiente, 0);
		//gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, luzDifusa, 0);
		//gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, luzEspecular, 0);

		// gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, posicaoLuz2, 0);
		// gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_AMBIENT, luzAmbiente, 0);
		// gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, luzDifusa, 0);
		// gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPECULAR, luzEspecular, 0);

		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_POSITION, posicaoLuz3, 0);
		gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_AMBIENT, luzAmbiente, 0);
		//gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_DIFFUSE, luzDifusa, 0);
		//gl.glLightfv(GL2.GL_LIGHT3, GL2.GL_SPECULAR, luzEspecular, 0);

		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT1);
		// gl.glEnable(GL2.GL_LIGHT2);
		gl.glEnable(GL2.GL_LIGHT3);

		// Reflexo vidro
		gl.glEnable(GL2.GL_BLEND);
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glEnable(GL2.GL_DEPTH_TEST);

	}



}
