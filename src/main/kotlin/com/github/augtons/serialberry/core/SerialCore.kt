package com.github.augtons.serialberry.core

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortInvalidPortException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SerialCore(
    val name: String,
    val baudrate: Long = 115200,
    val databit: Int = 8,
    val stopbit: Int = SerialPort.ONE_STOP_BIT,
    val parity: Int = SerialPort.NO_PARITY,
    val rts: Boolean = false,
    val dtr: Boolean = false,
    config: (SerialPort.() -> Unit)? = null
) {

    var internalSerialPort: SerialPort = SerialPort.getCommPort(name).apply {
            baudRate = baudRate; numDataBits = databit; numStopBits = databit; parity = parity;
    //            if (rts) { setRTS() } else { clearRTS() }
    //            if (dtr) { setDTR() } else { setDTR() }
        }
        private set

    init {
        // invoke config
        config?.let { internalSerialPort.it() }
    }

    fun config(config: SerialPort.() -> Unit) {
        config.let { internalSerialPort.it() }
    }

    fun open(): Boolean {
        return internalSerialPort.openPort()
    }

    fun destroy(): Boolean {
        return internalSerialPort.closePort()
    }

    fun readData() {
        CoroutineScope(Dispatchers.IO).launch {

        }
    }

    companion object {

        val DATA_BIT_ITEMS = listOf("5", "6", "7", "8", "9")

        val BAUDRATE_ITEMS = listOf("864000", "460800", "256000", "128000", "115200", "57600",
            "38400", "28800", "19200", "9600", "4800", "1200")

        val STOP_BIT_ITEMS = listOf("1" to SerialPort.ONE_STOP_BIT,
            "1.5" to SerialPort.ONE_POINT_FIVE_STOP_BITS,
            "2" to SerialPort.TWO_STOP_BITS)

        val PARITY_ITEM = listOf("No" to SerialPort.NO_PARITY,      // 不校验
            "Odd" to SerialPort.ODD_PARITY,    // 奇校验
            "Even" to SerialPort.EVEN_PARITY,  // 偶校验
            "Mark" to SerialPort.MARK_PARITY,  // 校验位始终为1
            "Space" to SerialPort.SPACE_PARITY) // 校验位始终为0
    }
}