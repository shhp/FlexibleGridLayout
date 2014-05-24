FlexibleGridLayout
=================

FlexibleGridLayout is a kind of grid layout that can automatically arrange the order of its child views. The child views need not to have identical width. When a child view is added to the layout, FlexibleGridLayout will put the view in the first suitable row which has enough space to hold the view. Once a child view is removed, FlexibleGridLayout will rearrange the remaining child views.

You can set horizontal and vertical space among child views by calling 
**setHorizontalMargin** and **setVerticalMargin** respectively.

If you want the FlexibleGridLayout automatically arrange the order of its child views, all child views should be added **programmatically** rather than in a XML file. If you do want to use FlexibleGridLayout in XML files, you can just regard FlexibleGridLayout as a RelativeLayout.


Developed By
============

* shhp - <herculeshhp@gmail.com>


License
=======

    Copyright 2014 shhp

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.