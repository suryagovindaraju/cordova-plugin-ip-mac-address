#import <Cordova/CDV.h>
#import <UIKit/UIKit.h>

@interface AddressImpl : CDVPlugin

- (void)getIPAddress:(CDVInvokedUrlCommand*)command;
- (void)getMACAddress:(CDVInvokedUrlCommand*)command;

@end
