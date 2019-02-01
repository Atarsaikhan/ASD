package application;

import gui.CDrawer;

public class CObserverTime implements IObserverTime {
	CDrawer drawer;
	
	public CObserverTime(CDrawer drawer) {
		System.out.println("time observer created");
		this.drawer = drawer;
		drawer.attach(this);
	}

	@Override
	public void update(int elapsedTime) {
		System.out.println("time observer update invoked");
		System.out.println("max time:"+drawer.getGameSettings().getTimer() +" current time:"+ elapsedTime);
		if (elapsedTime<=0) {
			System.out.println("time observer has to change state of drawer");
			drawer.timeOut();
		}
	}

}
