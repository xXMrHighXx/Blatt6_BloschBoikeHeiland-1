package skp_6;

import java.util.concurrent.SynchronousQueue;

public class SynchronousChannel extends SynchronousQueue<Integer>{

	private static final long serialVersionUID = 8975785526517311554L;
	
	public SynchronousChannel(){
		super(true);
	}
	
	public void send(Integer data) throws InterruptedException{
		put(data);
	}
	
	public Integer receive() throws InterruptedException{
		return take();
	}

}

