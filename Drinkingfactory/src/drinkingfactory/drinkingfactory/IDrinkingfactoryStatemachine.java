/** Generated by YAKINDU Statechart Tools code generator. */
package drinkingfactory.drinkingfactory;

import drinkingfactory.IStatemachine;
import drinkingfactory.ITimerCallback;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public interface IDrinkingfactoryStatemachine extends ITimerCallback,IStatemachine {
	public interface SCInterface {
	
		public void raiseDrinkSelected();
		
		public void raiseAddMoney();
		
		public void raisePaying();
		
		public void raisePaymentValidate();
		
		public void raiseResetMachine();
		
		public void raiseUserAction();
		
		public void raiseCupGrabbed();
		
		public void raiseHeatReached();
		
		public void raiseCupFilled();
		
		public boolean isRaisedDoResetMoney();
		
		public boolean isRaisedDoResetDrinkSetting();
		
		public boolean isRaisedDoResetMachine();
		
		public boolean isRaisedDoCleaning();
		
		public boolean isRaisedGrindBeans();
		
		public boolean isRaisedTakeTeaBag();
		
		public boolean isRaisedTakeOffTeaBag();
		
		public boolean isRaisedTakeCoffeePod();
		
		public boolean isRaisedHeatWater();
		
		public boolean isRaisedPutCup();
		
		public boolean isRaisedAddSugar();
		
		public boolean isRaisedPourWater();
		
		public boolean isRaisedGetDrink();
		
		public boolean isRaisedPutBeans();
		
		public boolean isRaisedCheckPayment();
		
		public long getDrinkNum();
		
		public void setDrinkNum(long value);
		
	public List<SCInterfaceListener> getListeners();
	}
	
	public interface SCInterfaceListener {
	
		public void onDoResetMoneyRaised();
		public void onDoResetDrinkSettingRaised();
		public void onDoResetMachineRaised();
		public void onDoCleaningRaised();
		public void onGrindBeansRaised();
		public void onTakeTeaBagRaised();
		public void onTakeOffTeaBagRaised();
		public void onTakeCoffeePodRaised();
		public void onHeatWaterRaised();
		public void onPutCupRaised();
		public void onAddSugarRaised();
		public void onPourWaterRaised();
		public void onGetDrinkRaised();
		public void onPutBeansRaised();
		public void onCheckPaymentRaised();
		}
	
	public SCInterface getSCInterface();
	
}
