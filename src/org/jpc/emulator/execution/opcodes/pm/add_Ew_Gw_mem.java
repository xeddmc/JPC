package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class add_Ew_Gw_mem extends Executable
{
    final Pointer op1;
    final int op2Index;

    public add_Ew_Gw_mem(int blockStart, Instruction parent)
    {
        super(blockStart, parent);
        op1 = new Pointer(parent.operand[0], parent.adr_mode);
        op2Index = Processor.getRegIndex(parent.operand[1].toString());
    }

    public Branch execute(Processor cpu)
    {
        Reg op2 = cpu.regs[op2Index];
        cpu.flagOp1 = op1.get16(cpu);
        cpu.flagOp2 = op2.get16();
        cpu.flagResult = (short)(cpu.flagOp1 + cpu.flagOp2);
        op1.set16(cpu, (short)cpu.flagResult);
        cpu.flagIns = UCodes.ADD16;
        cpu.flagStatus = OSZAPC;
        return Branch.None;
    }

    public boolean isBranch()
    {
        return false;
    }

    public String toString()
    {
        return this.getClass().getName();
    }
}