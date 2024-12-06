package emulator;

import emulator.debug.MemoryViewImage;
import emulator.graphics2D.IImage;
import emulator.graphics3D.IGraphics3D;
import ru.woesss.j2me.micro3d.TextureImpl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.IntBuffer;

public class EmulatorPlatform implements IEmulatorPlatform {

	public boolean isX64() {
		return true;
	}

	public String getName() {
		return "KEmulator nnx64";
	}

	public String getTitleName() {
		return "KEmnnx64";
	}

	public String getInfoString(String version) {
		return "KEmulator nnmod " + version + "\n\n"
				+ "\tMulti-Platform\n"
				+ "\t" + UILocale.get("ABOUT_INFO_EMULATOR", "Mobile Game Emulator") + "\n\n"
				+ UILocale.get("ABOUT_INFO_APIS", "Support APIs") + ":\n\n"
				+ "\tMIDP 2.0 (JSR118)\n"
				+ "\tNokiaUI 1.4\n"
				+ "\tSprint 1.0\n"
				+ "\tSiemens API\n"
				+ "\tWMA 1.0 (JSR120)\n"
				+ "\tM3G 1.1 (JSR184)\n"
				+ "\tOpenGL ES (JSR239)\n"
				+ "\tMascotCapsule v3\n"
				+ "\tSoftBank MEXA"
				;
	}

	public void loadLibraries() {
		System.setProperty("jna.nosys", "true");
		loadSWTLibrary();
		loadLWJGLNatives();
	}

	public boolean supportsMascotCapsule() {
		return true;
	}

	public boolean supportsM3G() {
		return true;
	}

	public MemoryViewImage convertMicro3DTexture(Object o) {
		try {
			Class cls = o.getClass();
			TextureImpl impl = (TextureImpl) cls.getField("impl").get(o);
			if (impl == null)
				return null;
			IntBuffer ib = impl.image.getRaster().asIntBuffer();
			int w = impl.getWidth(), h = impl.getHeight();
			IImage img = Emulator.getEmulator().newImage(w, h, true);
			int[] data = img.getData();
			int i = data.length - w;

			for (int j = h; j > 0; --j) {
				ib.get(data, i, w);
				i -= w;
			}
			return new MemoryViewImage(img);
		} catch (Exception ignored) {}
		return null;
	}

	public IGraphics3D getGraphics3D() {
		return emulator.graphics3D.lwjgl.Emulator3D.getInstance();
	}

	public void load3D() {
		if (supportsM3G()) {
			// load m3g
			boolean m3gLoaded = false;
			try {
				Class cls = Class.forName("javax.microedition.m3g.Graphics3D");
				try {
					m3gLoaded = !cls.getField("_STUB").getBoolean(null);
				} catch (Throwable e) {
					m3gLoaded = true;
				}
			} catch (Throwable ignored) {}
			if (!m3gLoaded) {
				// TODO
				addToClassPath("m3g_lwjgl.jar");
			}
		}

		// load mascot
		if (supportsMascotCapsule()) {
			boolean mascotLoaded = false;
			try {
				Class cls = Class.forName("com.mascotcapsule.micro3d.v3.Graphics3D");
				try {
					mascotLoaded = !cls.getField("_STUB").getBoolean(null);
				} catch (Throwable e) {
					mascotLoaded = true;
				}
			} catch (Throwable ignored) {}
			if (!mascotLoaded) {
				addToClassPath("micro3d_gl.jar");
			}
		}
	}

	private static void loadSWTLibrary() {
		String osn = System.getProperty("os.name").toLowerCase();
		String osa = System.getProperty("os.arch").toLowerCase();
		String os =
				osn.contains("win") ? "win32" :
						osn.contains("mac") ? "macosx" :
								osn.contains("linux") || osn.contains("nix") ? "gtk-linux" :
										null;
		if (os == null) {
			throw new RuntimeException("unsupported os: " + osn);
		}
		if (!osa.contains("amd64") && !osa.contains("86") && !osa.contains("aarch64") && !osa.contains("arm")) {
			throw new RuntimeException("unsupported arch: " + osa);
		}
		String arch = osa.contains("amd64") ? "x86_64" : osa.contains("86") ? "x86" : osa.contains("arm") ? "armhf" : osa;
		try {
			addToClassPath("swt-" + os + "-" + arch + ".jar");
		} catch (RuntimeException e) {
			// Check if SWT is already loaded
			try {
				Class.forName("org.eclipse.swt.SWT");
			} catch (ClassNotFoundException e2) {
				throw e;
			}
		}
	}

	private static void loadLWJGLNatives() {
		String osn = System.getProperty("os.name").toLowerCase();
		String osa = System.getProperty("os.arch").toLowerCase();
		String os =
				osn.contains("win") ? "windows" :
						osn.contains("mac") ? "macos" :
								osn.contains("linux") || osn.contains("nix") ? "linux" :
										null;
		if (os == null) {
			return;
		}
		if (!osa.contains("amd64") && !osa.contains("86") && !osa.contains("aarch64") && !osa.contains("arm")) {
			return;
		}
		String arch = os + (osa.contains("amd64") ? "" : osa.contains("86") ? "-x86" : osa.contains("aarch64") ? "-arm64" : osa.contains("arm") ? "-arm32" : "");
		try {
			addToClassPath("lwjgl-natives-" + arch + ".jar");
			addToClassPath("lwjgl-glfw-natives-" + arch + ".jar");
			addToClassPath("lwjgl-opengl-natives-" + arch + ".jar");
			addToClassPath("lwjgl3-swt-" + arch + ".jar");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	private static void addToClassPath(String s) {
		try {
			Agent.addClassPath(new File(Emulator.getAbsolutePath() + File.separatorChar + s));
		} catch (Exception e) {
			throw new RuntimeException(s, e);
		}
	}
}