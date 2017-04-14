package org.nerdizin.jcow;

public enum Command {
	moo,
	mOo,
	moO,
	mOO,
	Moo,
	MOo,
	MoO,
	MOO,
	OOO,
	MMM,
	OOM,
	oom;
	
	public static Command getByLiteral(final String input) {
		try {
			return valueOf(input);
		} catch(IllegalArgumentException e) {
			return null;
		}
	}
	
	public static Command getByIndex(final int index) {
		return values()[index];
	}
}
