package framework;

import java.util.ArrayList;
import java.util.List;

public class CCareTaker {
	
	private List<CMemento> mementoList = new ArrayList<CMemento>();

	public void add(CMemento game) {
		mementoList.add(game);
	}

	CMemento get(int index) {
		return mementoList.get(index);
	}
	
	public CMemento pop() {
		CMemento mem = mementoList.get(mementoList.size()-1);
		mementoList.remove(mementoList.size()-1);
		return mem;
	}
}