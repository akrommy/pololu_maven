package com.pololu;

import com.sun.jna.*;
import com.sun.jna.ptr.*;

public class App {
    public interface LibUSB extends Library {
        LibUSB INSTANCE = Native.load("libusb-1.0", LibUSB.class);

        int libusb_init(PointerByReference ctx);
        void libusb_exit(Pointer ctx);
        Pointer libusb_open_device_with_vid_pid(Pointer ctx, int vendor_id, int product_id);
        int libusb_control_transfer(Pointer dev, byte requestType, byte request, short value, short index, byte[] data, short length, int timeout);
    }

    public static void main(String[] args) {
        System.load("C:\\pololu_maven\\PololuReader\\lib\\libusb-1.0.dll");

        PointerByReference ctx = new PointerByReference();
        if (LibUSB.INSTANCE.libusb_init(ctx) != 0) {
            System.err.println("Failed to initialize libusb.");
            return;
        }

        int vendorId = 0x1FFB;
        int productId = 0x008A;
        Pointer device = LibUSB.INSTANCE.libusb_open_device_with_vid_pid(ctx.getValue(), vendorId, productId);

        if (device == null) {
            System.err.println("Failed to open Pololu device.");
            return;
        }
    }
}
