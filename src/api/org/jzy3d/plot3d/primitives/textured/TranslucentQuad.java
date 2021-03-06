package org.jzy3d.plot3d.primitives.textured;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import org.jzy3d.colors.Color;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Quad;
import org.jzy3d.plot3d.rendering.compat.GLES2CompatUtils;
import org.jzy3d.plot3d.rendering.view.Camera;

public class TranslucentQuad extends Quad implements ITranslucent {
	public void draw(GL gl, GLU glu, Camera cam) {
		// Execute transformation
		doTransform(gl, glu, cam);

		if (gl.isGL2()) {

			// Draw content of polygon
			if (facestatus) {
				gl.getGL2().glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
				if (wfstatus) {
					gl.glEnable(GL2.GL_POLYGON_OFFSET_FILL);
					gl.glPolygonOffset(1.0f, 1.0f);
				}
				gl.getGL2().glBegin(GL2.GL_QUADS); // <<<
				for (Point p : points) {
					if (mapper != null) {
						Color c = mapper.getColor(p.xyz); // TODO: should store
															// result in the
															// point color
						callWithAlphaFactor(gl, c, alpha);
					} else
						callWithAlphaFactor(gl, p.rgb, alpha);
					// System.out.println(p.rgb + " alpha factor = " + alpha);
					gl.getGL2().glVertex3f(p.xyz.x, p.xyz.y, p.xyz.z);
				}
				gl.getGL2().glEnd();
				if (wfstatus)
					gl.glDisable(GL2.GL_POLYGON_OFFSET_FILL);
			}

			// Draw edge of polygon
			if (wfstatus) {
				gl.getGL2().glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);

				gl.glEnable(GL2.GL_POLYGON_OFFSET_FILL);
				gl.glPolygonOffset(1.0f, 1.0f);

				callWithAlphaFactor(gl, wfcolor, alpha);
				gl.glLineWidth(wfwidth);

				gl.getGL2().glBegin(GL2.GL_QUADS);
				for (Point p : points) {
					gl.getGL2().glVertex3f(p.xyz.x, p.xyz.y, p.xyz.z);
				}
				gl.getGL2().glEnd();

				gl.glDisable(GL2.GL_POLYGON_OFFSET_FILL);
			}
		} else {
			// Draw content of polygon
			if (facestatus) {
				GLES2CompatUtils.glPolygonMode(GL2.GL_FRONT_AND_BACK,
						GL2.GL_FILL);
				if (wfstatus) {
					gl.glEnable(GL2.GL_POLYGON_OFFSET_FILL);
					gl.glPolygonOffset(1.0f, 1.0f);
				}
				GLES2CompatUtils.glBegin(GL2.GL_QUADS); // <<<
				for (Point p : points) {
					if (mapper != null) {
						Color c = mapper.getColor(p.xyz); // TODO: should store
															// result in the
															// point color
						callWithAlphaFactor(gl, c, alpha);
					} else
						callWithAlphaFactor(gl, p.rgb, alpha);
					// System.out.println(p.rgb + " alpha factor = " + alpha);
					GLES2CompatUtils.glVertex3f(p.xyz.x, p.xyz.y, p.xyz.z);
				}
				GLES2CompatUtils.glEnd();
				if (wfstatus)
					gl.glDisable(GL2.GL_POLYGON_OFFSET_FILL);
			}

			// Draw edge of polygon
			if (wfstatus) {
				GLES2CompatUtils.glPolygonMode(GL2.GL_FRONT_AND_BACK,
						GL2.GL_LINE);

				gl.glEnable(GL2.GL_POLYGON_OFFSET_FILL);
				gl.glPolygonOffset(1.0f, 1.0f);

				callWithAlphaFactor(gl, wfcolor, alpha);
				gl.glLineWidth(wfwidth);

				GLES2CompatUtils.glBegin(GL2.GL_QUADS);
				for (Point p : points) {
					GLES2CompatUtils.glVertex3f(p.xyz.x, p.xyz.y, p.xyz.z);
				}
				GLES2CompatUtils.glEnd();

				gl.glDisable(GL2.GL_POLYGON_OFFSET_FILL);
			}

		}
	}

	@Override
	public void setAlphaFactor(float a) {
		alpha = a;
	}

	protected float alpha = 1;
}
