//
//  AsyncFlashover.swift
//  FireCalc
//
//  Created by MAHAPATABENDIGE CHARMAL D on 3/11/18.
//  Copyright © 2018 tpcreative.co. All rights reserved.
//

//
//  AsynUser.swift
//  RestFul API
//
//  Created by phong on 9/10/16.
//  Copyright © 2016 tpcreative.co. All rights reserved.
//

import UIKit

class AsynFlashover: NSObject {
    
    var view : AnyObject?
    var flashover : Struct_FlashoverModel?
    var flashoverDefault : UserDefaults?
    
    init(view : AnyObject, flashover : Struct_FlashoverModel){
        
        self.view = view
        self.flashover = flashover
        self.flashoverDefault  = UserDefaults()
    }
    
}

