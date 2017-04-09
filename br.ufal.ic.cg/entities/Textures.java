package entities;

import java.io.File;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class Textures {

	int textureArc1;
	int textureArc2;
	
	
	int texture;
	int textureFloor;
	int textureWall;
	int textureTable;
	int textureDoor;
	int textureWindow;

	int textureWindow2;
	int textureWhiteBoard;
	int textureEdge;
	int textureCorredor;
	int textureRoof;
	int textureRoof2;

	int texturePC;
	int texturePCMonitor;
	int texturePCGabinete;
	int texturePCGabinete2;
	int texturePCMonitor2;
	int textureKeyboard;
	int textureFundo;
	int textureFrente;

	int textureArFrente;
	int textureArDir;
	int textureArDir2;
	int textureArEsq;
	int textureArEsq2;
	int textureArFrente2;
	int textureChair;
	int textureChair2;

	int textureJanelaLado;
	int textureJanelaEmPe;
	
	public void carregar_texturas(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		try {
			
			Texture textArc1 = TextureIO.newTexture(new File(getClass().getResource("/img/arc_texture.png").getPath()), true);
			textureArc1 = textArc1.getTextureObject(gl);
			
			Texture textArc2 = TextureIO.newTexture(new File(getClass().getResource("/img/arc2_texture.png").getPath()), true);
			textureArc2 = textArc1.getTextureObject(gl);

			Texture text = TextureIO.newTexture(new File(getClass().getResource("/img/piso2.jpg").getPath()), true);
			texture = text.getTextureObject(gl);

			Texture text2 = TextureIO.newTexture(new File(getClass().getResource("/img/parede3.jpg").getPath()), true);
			textureWall = text2.getTextureObject(gl);

			Texture text3 = TextureIO.newTexture(new File(getClass().getResource("/img/mesa2.jpg").getPath()), true);
			textureTable = text3.getTextureObject(gl);

			Texture text4 = TextureIO.newTexture(new File(getClass().getResource("/img/porta.jpg").getPath()), true);
			textureDoor = text4.getTextureObject(gl);

			Texture text5 = TextureIO.newTexture(new File(getClass().getResource("/img/janelaFrente.jpg").getPath()),
					true);
			textureWindow = text5.getTextureObject(gl);

			Texture text6 = TextureIO.newTexture(new File(getClass().getResource("/img/quadro.jpg").getPath()), true);
			textureWhiteBoard = text6.getTextureObject(gl);

			Texture text7 = TextureIO.newTexture(new File(getClass().getResource("/img/janelaTras.jpg").getPath()),
					true);
			textureWindow2 = text7.getTextureObject(gl);

			Texture text8 = TextureIO.newTexture(new File(getClass().getResource("/img/bordas.jpg").getPath()), true);
			textureEdge = text8.getTextureObject(gl);

			Texture text9 = TextureIO.newTexture(new File(getClass().getResource("/img/corredor.jpg").getPath()), true);
			textureCorredor = text9.getTextureObject(gl);

			Texture text10 = TextureIO.newTexture(new File(getClass().getResource("/img/teto.jpg").getPath()), true);
			textureRoof = text10.getTextureObject(gl);

			Texture text11 = TextureIO.newTexture(new File(getClass().getResource("/img/tetoCorredor.jpg").getPath()),
					true);
			textureRoof2 = text11.getTextureObject(gl);

			Texture text12 = TextureIO.newTexture(new File(getClass().getResource("/img/preto.jpg").getPath()), true);
			texturePC = text12.getTextureObject(gl);

			Texture text13 = TextureIO.newTexture(new File(getClass().getResource("/img/monitor.jpg").getPath()), true);
			texturePCMonitor = text13.getTextureObject(gl);

			Texture text14 = TextureIO.newTexture(new File(getClass().getResource("/img/gabinete.png").getPath()),
					true);
			texturePCGabinete = text14.getTextureObject(gl);

			Texture text15 = TextureIO.newTexture(new File(getClass().getResource("/img/monitor2.jpg").getPath()),
					true);
			texturePCMonitor2 = text15.getTextureObject(gl);

			Texture text16 = TextureIO.newTexture(new File(getClass().getResource("/img/gabinete2.png").getPath()),
					true);
			texturePCGabinete2 = text16.getTextureObject(gl);

			Texture text17 = TextureIO.newTexture(new File(getClass().getResource("/img/keyboard.png").getPath()),
					true);
			textureKeyboard = text17.getTextureObject(gl);

			Texture text18 = TextureIO.newTexture(new File(getClass().getResource("/img/fundo.jpg").getPath()), true);
			textureFundo = text18.getTextureObject(gl);

			Texture text19 = TextureIO.newTexture(new File(getClass().getResource("/img/frente.jpg").getPath()), true);
			textureFrente = text19.getTextureObject(gl);

			Texture text20 = TextureIO.newTexture(new File(getClass().getResource("/img/arFrente.jpg").getPath()),
					true);
			textureArFrente = text20.getTextureObject(gl);

			Texture text21 = TextureIO.newTexture(new File(getClass().getResource("/img/arFrente2.jpg").getPath()),
					true);
			textureArFrente2 = text21.getTextureObject(gl);

			Texture text22 = TextureIO.newTexture(new File(getClass().getResource("/img/arEsq.jpg").getPath()), true);
			textureArEsq = text22.getTextureObject(gl);

			Texture text23 = TextureIO.newTexture(new File(getClass().getResource("/img/arEsq2.jpg").getPath()), true);
			textureArEsq2 = text23.getTextureObject(gl);

			Texture text24 = TextureIO.newTexture(new File(getClass().getResource("/img/arDir.jpg").getPath()), true);
			textureArDir = text24.getTextureObject(gl);

			Texture text25 = TextureIO.newTexture(new File(getClass().getResource("/img/arDir2.jpg").getPath()), true);
			textureArDir2 = text25.getTextureObject(gl);

			Texture text26 = TextureIO.newTexture(new File(getClass().getResource("/img/cadeira.jpg").getPath()), true);
			textureChair = text26.getTextureObject(gl);

			Texture text27 = TextureIO.newTexture(new File(getClass().getResource("/img/cadeira2.jpg").getPath()),
					true);
			textureChair2 = text27.getTextureObject(gl);

			Texture text28 = TextureIO.newTexture(new File(getClass().getResource("/img/piso3.jpg").getPath()), true);
			textureFloor = text28.getTextureObject(gl);

			Texture text29 = TextureIO.newTexture(new File(getClass().getResource("/img/janelaLado.png").getPath()),
					true);
			textureJanelaLado = text29.getTextureObject(gl);

			Texture text30 = TextureIO.newTexture(new File(getClass().getResource("/img/janelaEmPe.png").getPath()),
					true);
			textureJanelaEmPe = text30.getTextureObject(gl);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getTextureArc1() {
		return textureArc1;
	}
	
	public int getTextureArc2() {
		return textureArc2;
	}
	
	


	
}
