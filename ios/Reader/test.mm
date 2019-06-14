//
//  test.m
//  Reader
//
//  Created by 李润泽 on 2019/6/13.
//  Copyright © 2019 李润泽. All rights reserved.
//

#import "aaa.h"
#import "testC.hpp"

using namespace std;

@implementation aaa

- (int)test {
    testC* cppTest = new testC();
    return cppTest->test();
}

@end
