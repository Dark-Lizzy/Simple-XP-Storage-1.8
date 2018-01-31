package com.darkliz.simplexpstorage.proxy;

import com.darkliz.simplexpstorage.init.SXPSItems;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerRenders()
	{
		SXPSItems.registerRenders();
	}
	

}
