package Framework;

import java.util.ArrayList;
import java.util.List;

class CCareTaker {
	
	private List<CMemento> mementoList = new ArrayList<CMemento>();

	void add(CMemento game) {
		mementoList.add(game);
	}

	CMemento get(int index) {
		return mementoList.get(index);
	}
}