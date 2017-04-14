package org.nerdizin.jcow;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CowInterpreter {

	private List<Command> commands;
	private List<Integer> memory;
	private Integer register;
	private int index;

	public void run(final String program) {
		init();
		commands = Pattern.compile("\\s+")
				.splitAsStream(program)
				.map(Command::getByLiteral)
				.collect(Collectors.toList());

		final ListIterator<Command> it = commands.listIterator();
		while (it.hasNext()) {
			final Command command = it.next();
			if (command != null) {
				handleCommand(command, it);
			}
		}
	}

	private void handleCommand(final Command command, final ListIterator<Command> it) {
		switch (command) {
		case moO:
			moO();
			break;
		case mOo:
			mOo();
			break;
		case OOO:
			OOO();
			break;
		case oom:
			oom();
			break;
		case OOM:
			OOM();
			break;
		case MoO:
			MoO();
			break;
		case MOo:
			MOo();
			break;
		case MMM:
			MMM();
			break;
		case Moo:
			Moo();
			break;
		case moo:
			moo(it);
			break;
		case mOO:
			mOO(it);
			break;
		case MOO:
			MOO(it);
			break;
		default:
			break;
		}
		//dumpState(command, 100);
	}

	private void MOo() {
		write(read() - 1);
	}

	private void MoO() {
		write(read() + 1);
	}

	private void OOM() {
		writeToStdOut(read());
	}

	private void oom() {
		write(readFromStdIn());
	}

	private void OOO() {
		write(0);
	}

	private void moO() {
		index++;
	}

	private void mOo() {
		if (index == 0) {
			System.exit(-1);
		}
		index--;
	}

	private void Moo() {
		if (read() == 0) {
			write(readAsciiFromStdIn());
		} else {
			writeAsciiToStdOut(read());
		}
	}

	private void MMM() {
		if (register == null) {
			register = read();
		} else {
			write(register);
			register = null;
		}
	}
	
	private void MOO(final ListIterator<Command> it) {
		if (read() == 0) {
			if (!it.hasNext()) {
				System.exit(-1);
			}
			it.next();
			int level = 1;
			while (it.hasNext() && level > 0) {
				final Command next = it.next();
				if (next == Command.MOO) {
					level++;
				} else if (next == Command.moo) {
					level--;
				}
			}
			if (level != 0) {
				System.exit(-1);
			}
		}
	}

	private void moo(final ListIterator<Command> it) {
		if (!it.hasPrevious()) {
			System.exit(-1);
		}
		it.previous();
		int level = 1;
		while (it.hasPrevious() && level > 0) {
			final Command previous = it.previous();
			if (previous == Command.moo) {
				level++;
			} else if (previous == Command.MOO) {
				level--;
			}
		}
		it.previous();
		if (level != 0) {
			System.exit(-1);
		}
	}
	
	private void mOO(final ListIterator<Command> it) {
		final int commandIndex = read();
		if (commandIndex == 3 || commandIndex < 0 || commandIndex > 11) {
			System.exit(0);
		}
		final Command command = Command.getByIndex(commandIndex);
		handleCommand(command, it);
	}
	
	private int readFromStdIn() {
		return 42;
	}

	private int readAsciiFromStdIn() {
		return (int) 'x';
	}

	private void writeToStdOut(final int value) {
		System.out.println(value);
	}

	private void writeAsciiToStdOut(final int value) {
		System.out.println((char) value);
	}

	public void write(final int value) {
		padMemory();
		memory.set(index, value);
	}

	public int read() {
		padMemory();
		if (index < 0) {
			return 0;
		}
		return memory.get(index);
	}

	private void init() {
		index = 0;
		register = null;
		commands = new ArrayList<>();
		memory = new ArrayList<>();
	}
	
	private void padMemory() {
		if (memory.size() <= index) {
			for (int i = 0; i < index - memory.size() + 1; i++) {
				memory.add(0);
			}
		}
	}
	
	private void dumpState(final Command command, final long delayMs) {
		final StringBuffer sb = new StringBuffer();
		sb.append(command + ", index: " + index + ", mem:");
		for(int i: memory) {
			sb.append(" " + i);
		}
		sb.append(", register: " + register);
		System.out.println(sb.toString());
		try {
			Thread.sleep(delayMs);
		} catch (InterruptedException ignore) {
		}
	}
}
