package me.flockshot.factionupgrades.utils.files.identifier;


public enum MessageIdentifier implements FileIdentifier
{
	NO_CLAIMS_LEFT, NO_MONEY, MAX_LEVEL, NO_FACTION, WARP_LIMIT, PLAYER_LIMIT, SIPHON_MAX_LEVEL, TNT_BANK_FULL ;
	//NULL_NAME;
	
	
	
	
	
	
	
	@Override
	public String toString()
	{
		return super.toString().replace("$", ".");
	}
}
