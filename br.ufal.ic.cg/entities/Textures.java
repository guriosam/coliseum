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

	int face1;

	int door;
	
	int textureSand;
	int floor;
	int laranja;
	int folha;
	int madeira;
	int pedra;
	int pedraEscura;

	public void carregar_texturas(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		try {

			Texture textArc1 = TextureIO.newTexture(new File(getClass().getResource("/img/arc_texture.png").getPath()),
					true);
			textureArc1 = textArc1.getTextureObject(gl);

			Texture textArc2 = TextureIO.newTexture(new File(getClass().getResource("/img/arc2_texture.png").getPath()),
					true);
			textureArc2 = textArc1.getTextureObject(gl);

			Texture text = TextureIO.newTexture(new File(getClass().getResource("/img/piso2.jpg").getPath()), true);
			texture = text.getTextureObject(gl);

			Texture piso = TextureIO.newTexture(new File(getClass().getResource("/img/floor3.jpg").getPath()), true);
			floor = piso.getTextureObject(gl);

			Texture text2 = TextureIO.newTexture(new File(getClass().getResource("/img/walls_coliseu.jpg").getPath()),
					true);
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

			Texture text11 = TextureIO.newTexture(new File(getClass().getResource("/img/walls_coliseu.jpg").getPath()),
					true);
			textureWall = text11.getTextureObject(gl);

			Texture text12 = TextureIO.newTexture(new File(getClass().getResource("/img/areia.jpg").getPath()), true);
			textureSand = text12.getTextureObject(gl);

			Texture text13 = TextureIO.newTexture(new File(getClass().getResource("/img/face2.jpg").getPath()), true);
			face1 = text13.getTextureObject(gl);

			Texture text14 = TextureIO.newTexture(new File(getClass().getResource("/img/laranja.jpg").getPath()), true);
			laranja = text14.getTextureObject(gl);

			Texture text15 = TextureIO.newTexture(new File(getClass().getResource("/img/folha.jpg").getPath()), true);
			folha = text15.getTextureObject(gl);

			Texture text16 = TextureIO.newTexture(new File(getClass().getResource("/img/madeira.jpg").getPath()), true);
			madeira = text16.getTextureObject(gl);
			
			Texture text17 = TextureIO.newTexture(new File(getClass().getResource("/img/pedra.jpg").getPath()), true);
			pedra = text17.getTextureObject(gl);
			
			Texture text18 = TextureIO.newTexture(new File(getClass().getResource("/img/pedra2.jpg").getPath()), true);
			pedraEscura = text18.getTextureObject(gl);
			
			Texture text19 = TextureIO.newTexture(new File(getClass().getResource("/img/door.jpg").getPath()), true);
			door = text19.getTextureObject(gl);

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
