package Framework;

import java.util.ArrayList;
import java.util.List;

class CCareTaker {
	
	private List<Memento> mementoList = new ArrayList<Memento>();

	void add(Memento game) {
		mementoList.add(game);
	}

	Memento get(int index) {
		return mementoList.get(index);
	}
}