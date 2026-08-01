package basic.zKernel;

import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zKernel.file.ini.IIniTagWithExpressionZZZ;
import custom.zKernel.ConfigZZZ;

/**
 * @author 0823
 * @deprecated 20181020: Der direkte Einsatz dieser Klasse wird durch
 *             KernelSingletonZZZ ersetzt.
 *
 *             To change this generated comment edit the template variable
 *             "typecomment": Window>Preferences>Java>Templates. To enable and
 *             disable the creation of type comments go to
 *             Window>Preferences>Java>Code Generation.
 */
@Deprecated
public class KernelZZZ extends AbstractKernelObjectZZZ implements IObjectZZZ, IKernelContextUserZZZ {
	private static final long serialVersionUID = -2734877463371564724L;

	/**
	 * Verwende diesen Konstruktor, wenn die Defaultangaben für das Verzeichnis und
	 * für den ini-Dateinamen verwendet werden sollen: -Verzeichnis:
	 * c:\\fglKernel\\KernelConfig - Datei: ZKernelConfigKernel_default.ini Diese
	 * Werte stammen aus ConfigZZZ im custom.zKernel - Verzeichnis. lindhauer;
	 * 14.08.2007 06:40:31
	 * 
	 * @throws ExceptionZZZ
	 * @depracated Der direkte Einsatz des Konstruktors dieser Klasse wird durch
	 *             KernelSingletonZZZ.getInstance() ersetzt.
	 */
	public KernelZZZ() throws ExceptionZZZ {
		super();
	}

	public KernelZZZ(String[] saFlagControl) throws ExceptionZZZ {
		super(saFlagControl);
	}

	public KernelZZZ(String[] saArg, String[] saFlagControl) throws ExceptionZZZ {
		super(saArg, saFlagControl);
	}

	/**
	 * Merke: Damit einzelne Projekte ihr eigenes ConfigZZZ - Objekt verwenden
	 * k�nnen, wird in diesem Konstruktor ein Interface eingebaut. lindhauer;
	 * 14.08.2007 07:19:55
	 * 
	 * @param objConfig
	 * @param sFlagControl
	 * @throws ExceptionZZZ
	 */
	public KernelZZZ(IKernelConfigZZZ objConfig, String sFlagControl) throws ExceptionZZZ {
		super(objConfig, sFlagControl);

	}

	public KernelZZZ(IKernelConfigZZZ objConfig, String[] saFlagControl) throws ExceptionZZZ {
		super(objConfig, saFlagControl);
	}

	public KernelZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName,
			String[] saFlagControl) throws ExceptionZZZ {
		super(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);
	}

	public KernelZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName,
			String[] saArg, String[] saFlagControl) throws ExceptionZZZ {
		super(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, saArg, saFlagControl);
	}

	public KernelZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName,
			String sFlagControl) throws ExceptionZZZ {
		super(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, sFlagControl);
	}

	public KernelZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName,
			String[] saArg, String sFlagControl) throws ExceptionZZZ {
		super(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, saArg, sFlagControl);
	}

	public KernelZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName,
			IKernelContextZZZ objContext, String sFlagControl) throws ExceptionZZZ {
		super(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, objContext, sFlagControl);
	}

	public KernelZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName,
			IKernelContextZZZ objContext, String[] saArg, String sFlagControl) throws ExceptionZZZ {
		super(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, objContext, saArg, sFlagControl);
	}

	/**
	 * Verwende diesen Konstruktor, wenn die Defaultangaben f�r das Verzeichnis und
	 * f�r den ini-Dateinamen verwendet werden sollen: -Verzeichnis:
	 * c:\\fglKernel\\KernelConfig - Datei: ZKernelConfigKernel_default.ini Diese
	 * Werte stammen aus ConfigZZZ im custom.zKernel - Verzeichnis.
	 * 
	 * lindhauer; 14.08.2007 06:34:16
	 * 
	 * @param sApplicationKey
	 * @param sSystemNumber
	 * @param sFlagControl
	 * @throws ExceptionZZZ
	 */
	public KernelZZZ(String sApplicationKey, String sSystemNumber, String sFlagControl) throws ExceptionZZZ {
		super(sApplicationKey, sSystemNumber, sFlagControl);
	}

	public KernelZZZ(String sApplicationKey, String sSystemNumber, String[] saArg, String sFlagControl)
			throws ExceptionZZZ {
		super(sApplicationKey, sSystemNumber, saArg, sFlagControl);
	}

	/**
	 * Verwende diesen Konstruktor, wenn die Defaultangaben f�r das Verzeichnis und
	 * f�r den ini-Dateinamen verwendet werden sollen: -Verzeichnis:
	 * c:\\fglKernel\\KernelConfig - Datei: ZKernelConfigKernel_default.ini Diese
	 * Werte stammen aus ConfigZZZ im custom.zKernel - Verzeichnis.
	 * 
	 * lindhauer; 14.08.2007 06:45:43
	 * 
	 * @param sApplicationKey
	 * @param sSystemNumber
	 * @param saFlagControl
	 * @throws ExceptionZZZ
	 */
	public KernelZZZ(String sApplicationKey, String sSystemNumber, String[] saFlagControl) throws ExceptionZZZ {
		super(sApplicationKey, sSystemNumber, saFlagControl);
	}

	/**
	 * Choose this construktor, if you want to create another Kernel-Object, using
	 * the same configuration and log-file, but another Application Lindhauer;
	 * 08.05.2006 08:21:25
	 * 
	 * @param sApplicationKey
	 * @param objKernelNew
	 * @param saFlagControl
	 * @throws ExceptionZZZ
	 */
	public KernelZZZ(String sApplicationKey, String sSystemNumber, IKernelZZZ objKernelOld, String[] saFlagControl)
			throws ExceptionZZZ {
		super(sApplicationKey, sSystemNumber, objKernelOld, saFlagControl);
	}

	/**
	 * Choose this construktor, if you want to create another Kernel-Object, using
	 * the same configuration and log-file, but another Application PLUS: Pass other
	 * FlagZ-Values to the new Kernel-Object. Lindhauer; 08.05.2006 08:21:25
	 * 
	 * @param sApplicationKey
	 * @param objKernelNew
	 * @param saFlagControl
	 * @throws ExceptionZZZ
	 */
	public KernelZZZ(String sApplicationKey, String sSystemNumber, IKernelZZZ objKernelOld, String[] saArg,
			String[] saFlagControl) throws ExceptionZZZ {
		super(sApplicationKey, sSystemNumber, objKernelOld, saArg, saFlagControl);
	}

	// ### Interface
	public IKernelConfigZZZ getConfigObject() throws ExceptionZZZ {
		IKernelConfigZZZ objConfig = super.getConfigObject();
		if (objConfig == null) {
			objConfig = new ConfigZZZ();
		}
		return objConfig;
	}

	// aus IResscourceHandlingObjectZZZ
	// ### Ressourcen werden anders geholt, wenn die Klasse in einer JAR-Datei
	// gepackt ist. Also:
	/**
	 * Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss,
	 * wenn die Applikation in einer JAR-Datei läuft. Merke: Static Klassen müssen
	 * diese Methode selbst implementieren.
	 * 
	 * @return
	 * @author lindhaueradmin, 21.02.2019
	 * @throws ExceptionZZZ
	 */
	@Override
	public boolean isInJar() throws ExceptionZZZ {
		boolean bReturn = false;
		main: {
			bReturn = JarEasyUtilZZZ.isInJar(this.getClass());
		}
		return bReturn;
	}

	/**
	 * Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss,
	 * wenn die Applikation in einer JAR-Datei läuft. Merke: Static Klassen müssen
	 * diese Methode selbst implementieren. Das ist dann das Beispiel.
	 * 
	 * @return
	 * @author lindhaueradmin, 21.02.2019
	 * @throws ExceptionZZZ
	 */
	public static boolean isInJarStatic() throws ExceptionZZZ {
		boolean bReturn = false;
		main: {
			bReturn = JarEasyUtilZZZ.isInJar(KernelZZZ.class);
		}
		return bReturn;
	}

	// ### Aus IObjectWithExpression
	@Override
	public boolean isExpressionEnabledGeneral() throws ExceptionZZZ {
		return this.getFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION);
	}
}// end class// end class
