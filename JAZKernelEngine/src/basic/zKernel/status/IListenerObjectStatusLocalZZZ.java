package basic.zKernel.status;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public interface IListenerObjectStatusLocalZZZ extends IListenerObjectStatusBasicZZZ{
	//Hier wird ggfs. der empfangene Status mit einem Alias gemappt. 
	//Der Alias kann dann in der "reaction"-Methode verwendet werden um gezielt zu reagieren.
	//Merke: Ohne HashMap oder mit leerer HashMap ist jede Action relevant.
	//Merke: Es können als Action-Angabe auch verwendet werden
	//       KernelZFormulaIni_NullZZZ.getExpressionTagNull(), d.h. ist NICHT relevant, s. HashMap=NULL 
	//       KernelZFormulaIni_NullZZZ.getExpressionTagEmpty(), d.h. ist auf jeden Fall relevant, ohne spezielle Differenzierung. Wie "".
	
	//Eine HashMap mit den StatusLocal-Enums und einem ActionAlias.
	//Hier macht es nur Sinn StatusLocal-Enums aufzunehmen von Objekten, an denen das Objekt selbst als  Listener registriert ist.
	//Die definierten ActionAliassse werden verwendet in der Methode: reactOnStatusLocalEvent4ActionCustom
	public HashMap<IEnumSetMappedStatusLocalZZZ,String> createHashMapStatusLocal4ReactionCustom_String();
	public HashMap<IEnumSetMappedStatusLocalZZZ,String> getHashMapStatusLocal4Reaction_String();	
	public void setHashMapStatusLocal4Reaction_String(HashMap<IEnumSetMappedStatusLocalZZZ,String> hmEnumSetForReaction);	
	
	
	//dito, nur eine HashMap mit einem Enum, statt einem action Alias.
	public HashMap<IEnumSetMappedStatusLocalZZZ,IEnumSetMappedZZZ> createHashMapStatusLocal4ReactionCustom_Enum();
	public HashMap<IEnumSetMappedStatusLocalZZZ,IEnumSetMappedZZZ> getHashMapStatusLocal4Reaction_Enum(); 	
	public void setHashMapStatusLocal4Reaction_Enum(HashMap<IEnumSetMappedStatusLocalZZZ,IEnumSetMappedZZZ> hmEnumSetForReaction);	
	
	
	//dito, nur eine HashMap mit einem Enum, das auch einen Status hat. Mappe also einen Status auf einen eigenen Status (Z.B. verwendet in den Monitor-Objekten)(Z.B. verwendet in den ClientTray/ServerTray Objekten des OVPN Projekts)	
	public HashMap<IEnumSetMappedStatusLocalZZZ,IEnumSetMappedStatusLocalZZZ> createHashMapStatusLocal4ReactionCustom_EnumStatus();
	public HashMap<IEnumSetMappedStatusLocalZZZ,IEnumSetMappedStatusLocalZZZ> getHashMapStatusLocal4Reaction_EnumStatus(); 	
	public void setHashMapStatusLocal4Reaction_EnumStatus(HashMap<IEnumSetMappedStatusLocalZZZ,IEnumSetMappedStatusLocalZZZ> hmEnumSetForReaction);	
	
	//+++++++++++++++++++++++++++++++++++++++++++++
	public String getActionAliasString(IEnumSetMappedStatusLocalZZZ enumStatus);
		
	public boolean queryReactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	public boolean queryReactOnStatusLocalEventCustom(IEventObjectStatusLocalZZZ eventStatusLocal)throws ExceptionZZZ; //Darueber wird abgeprueft, ob eine Reaktion auf ein Event erfolgen soll. Z.B. beendete Programme koennen so eine Reaktion unterbinden.
	
	
	public boolean queryReactOnStatusLocalEvent4Action(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;	
	public boolean reactOnStatusLocalEvent4Action(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	//Hierin werden die ActionAliasse aus der HashMap createHashMapStatusLocal4ReactionCustom
	//in einer Fallunterscheidung zurm Aufruf der jeweiligen Methode verwendet.
	public boolean queryReactOnStatusLocal4Action(String sActionAlias, IEnumSetMappedStatusLocalZZZ enumStatus, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ;
	public boolean queryReactOnStatusLocal4ActionCustom(String sActionAlias, IEnumSetMappedStatusLocalZZZ enumStatus, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ;
	public boolean reactOnStatusLocal4Action(String sActionAlias, IEnumSetMappedStatusLocalZZZ enumStatus, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ;
	public boolean reactOnStatusLocal4ActionCustom(String sActionAlias, IEnumSetMappedStatusLocalZZZ enumStatus, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ;
	
	public boolean isEventRelevantAny(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ; //also überhaupt entweder für den eingenen Statuswechsel relevant oder fuer eine Reaction
	public boolean isEventRelevant4ReactionOnStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	public boolean isEventRelevant2ChangeStatusLocalByClass(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	public boolean isEventRelevant2ChangeStatusLocalByStatusLocalValue(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
}
