import serial # pyserial

try:
    # 端口：CNU； Linux上的/dev /ttyUSB0等； windows上的COM3等
    portx = "/dev/ttys0"

    # 波特率，标准值有：50,75,110,134,150,200,300,600,1200,1800,2400,4800,9600,19200,38400,57600,115200
    bps = 115200

    # 超时设置，None：永远等待操作；
    #         0：立即返回请求结果；
    #        其他：等待超时时间（单位为秒）
    timex = None

    # 打开串口，并得到串口对象
    ser = serial.Serial(portx, bps, timeout=timex)
    print("串口详情参数：", ser)

    # # 十六进制的发送
    # result = ser.write(chr(0x06).encode("utf-8")) # 写数据
    # print("写总字节数：", result)

    # 十六进制的读取
    print(ser.read().hex()) # 读一个字节

    print("----------")
    ser.close() # 关闭串口

except Exception as e:
    print("error!", e)