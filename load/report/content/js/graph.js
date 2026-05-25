/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
$(document).ready(function() {

    $(".click-title").mouseenter( function(    e){
        e.preventDefault();
        this.style.cursor="pointer";
    });
    $(".click-title").mousedown( function(event){
        event.preventDefault();
    });

    // Ugly code while this script is shared among several pages
    try{
        refreshHitsPerSecond(true);
    } catch(e){}
    try{
        refreshResponseTimeOverTime(true);
    } catch(e){}
    try{
        refreshResponseTimePercentiles();
    } catch(e){}
});


var responseTimePercentilesInfos = {
        data: {"result": {"minY": 36.0, "minX": 0.0, "maxY": 997.0, "series": [{"data": [[0.0, 36.0], [0.1, 36.0], [0.2, 36.0], [0.3, 36.0], [0.4, 40.0], [0.5, 40.0], [0.6, 40.0], [0.7, 40.0], [0.8, 47.0], [0.9, 47.0], [1.0, 47.0], [1.1, 47.0], [1.2, 47.0], [1.3, 50.0], [1.4, 50.0], [1.5, 50.0], [1.6, 52.0], [1.7, 52.0], [1.8, 52.0], [1.9, 52.0], [2.0, 56.0], [2.1, 56.0], [2.2, 56.0], [2.3, 56.0], [2.4, 56.0], [2.5, 56.0], [2.6, 56.0], [2.7, 56.0], [2.8, 60.0], [2.9, 60.0], [3.0, 60.0], [3.1, 60.0], [3.2, 68.0], [3.3, 68.0], [3.4, 68.0], [3.5, 68.0], [3.6, 69.0], [3.7, 69.0], [3.8, 69.0], [3.9, 69.0], [4.0, 70.0], [4.1, 70.0], [4.2, 70.0], [4.3, 70.0], [4.4, 72.0], [4.5, 72.0], [4.6, 72.0], [4.7, 72.0], [4.8, 72.0], [4.9, 72.0], [5.0, 72.0], [5.1, 72.0], [5.2, 73.0], [5.3, 73.0], [5.4, 73.0], [5.5, 73.0], [5.6, 74.0], [5.7, 74.0], [5.8, 74.0], [5.9, 74.0], [6.0, 74.0], [6.1, 74.0], [6.2, 74.0], [6.3, 74.0], [6.4, 75.0], [6.5, 75.0], [6.6, 75.0], [6.7, 75.0], [6.8, 75.0], [6.9, 75.0], [7.0, 75.0], [7.1, 75.0], [7.2, 76.0], [7.3, 76.0], [7.4, 76.0], [7.5, 76.0], [7.6, 77.0], [7.7, 77.0], [7.8, 77.0], [7.9, 77.0], [8.0, 78.0], [8.1, 78.0], [8.2, 78.0], [8.3, 78.0], [8.4, 79.0], [8.5, 79.0], [8.6, 79.0], [8.7, 79.0], [8.8, 80.0], [8.9, 80.0], [9.0, 80.0], [9.1, 80.0], [9.2, 80.0], [9.3, 82.0], [9.4, 82.0], [9.5, 82.0], [9.6, 82.0], [9.7, 83.0], [9.8, 83.0], [9.9, 83.0], [10.0, 83.0], [10.1, 84.0], [10.2, 84.0], [10.3, 84.0], [10.4, 84.0], [10.5, 85.0], [10.6, 85.0], [10.7, 85.0], [10.8, 85.0], [10.9, 87.0], [11.0, 87.0], [11.1, 87.0], [11.2, 87.0], [11.3, 88.0], [11.4, 88.0], [11.5, 88.0], [11.6, 88.0], [11.7, 88.0], [11.8, 88.0], [11.9, 88.0], [12.0, 88.0], [12.1, 90.0], [12.2, 90.0], [12.3, 90.0], [12.4, 90.0], [12.5, 92.0], [12.6, 92.0], [12.7, 92.0], [12.8, 92.0], [12.9, 93.0], [13.0, 93.0], [13.1, 93.0], [13.2, 93.0], [13.3, 94.0], [13.4, 94.0], [13.5, 94.0], [13.6, 94.0], [13.7, 97.0], [13.8, 97.0], [13.9, 97.0], [14.0, 97.0], [14.1, 97.0], [14.2, 97.0], [14.3, 97.0], [14.4, 97.0], [14.5, 97.0], [14.6, 97.0], [14.7, 97.0], [14.8, 97.0], [14.9, 97.0], [15.0, 97.0], [15.1, 97.0], [15.2, 97.0], [15.3, 99.0], [15.4, 99.0], [15.5, 99.0], [15.6, 99.0], [15.7, 99.0], [15.8, 99.0], [15.9, 99.0], [16.0, 99.0], [16.1, 100.0], [16.2, 100.0], [16.3, 100.0], [16.4, 100.0], [16.5, 101.0], [16.6, 101.0], [16.7, 101.0], [16.8, 105.0], [16.9, 105.0], [17.0, 105.0], [17.1, 105.0], [17.2, 107.0], [17.3, 107.0], [17.4, 107.0], [17.5, 107.0], [17.6, 108.0], [17.7, 108.0], [17.8, 108.0], [17.9, 108.0], [18.0, 110.0], [18.1, 110.0], [18.2, 110.0], [18.3, 110.0], [18.4, 110.0], [18.5, 110.0], [18.6, 110.0], [18.7, 110.0], [18.8, 111.0], [18.9, 111.0], [19.0, 111.0], [19.1, 111.0], [19.2, 111.0], [19.3, 111.0], [19.4, 111.0], [19.5, 111.0], [19.6, 112.0], [19.7, 112.0], [19.8, 112.0], [19.9, 112.0], [20.0, 112.0], [20.1, 112.0], [20.2, 112.0], [20.3, 112.0], [20.4, 114.0], [20.5, 114.0], [20.6, 114.0], [20.7, 114.0], [20.8, 114.0], [20.9, 114.0], [21.0, 114.0], [21.1, 114.0], [21.2, 115.0], [21.3, 115.0], [21.4, 115.0], [21.5, 115.0], [21.6, 115.0], [21.7, 115.0], [21.8, 115.0], [21.9, 115.0], [22.0, 115.0], [22.1, 115.0], [22.2, 115.0], [22.3, 115.0], [22.4, 119.0], [22.5, 119.0], [22.6, 119.0], [22.7, 119.0], [22.8, 119.0], [22.9, 119.0], [23.0, 119.0], [23.1, 119.0], [23.2, 119.0], [23.3, 119.0], [23.4, 119.0], [23.5, 119.0], [23.6, 122.0], [23.7, 122.0], [23.8, 122.0], [23.9, 122.0], [24.0, 122.0], [24.1, 122.0], [24.2, 122.0], [24.3, 122.0], [24.4, 124.0], [24.5, 124.0], [24.6, 124.0], [24.7, 124.0], [24.8, 124.0], [24.9, 124.0], [25.0, 124.0], [25.1, 124.0], [25.2, 126.0], [25.3, 126.0], [25.4, 126.0], [25.5, 126.0], [25.6, 129.0], [25.7, 129.0], [25.8, 129.0], [25.9, 129.0], [26.0, 131.0], [26.1, 131.0], [26.2, 131.0], [26.3, 131.0], [26.4, 131.0], [26.5, 131.0], [26.6, 131.0], [26.7, 131.0], [26.8, 132.0], [26.9, 132.0], [27.0, 132.0], [27.1, 132.0], [27.2, 132.0], [27.3, 132.0], [27.4, 132.0], [27.5, 132.0], [27.6, 132.0], [27.7, 132.0], [27.8, 132.0], [27.9, 132.0], [28.0, 138.0], [28.1, 138.0], [28.2, 138.0], [28.3, 138.0], [28.4, 140.0], [28.5, 140.0], [28.6, 140.0], [28.7, 140.0], [28.8, 144.0], [28.9, 144.0], [29.0, 144.0], [29.1, 144.0], [29.2, 145.0], [29.3, 145.0], [29.4, 145.0], [29.5, 145.0], [29.6, 145.0], [29.7, 145.0], [29.8, 145.0], [29.9, 145.0], [30.0, 146.0], [30.1, 146.0], [30.2, 146.0], [30.3, 146.0], [30.4, 147.0], [30.5, 147.0], [30.6, 147.0], [30.7, 147.0], [30.8, 148.0], [30.9, 148.0], [31.0, 148.0], [31.1, 148.0], [31.2, 150.0], [31.3, 150.0], [31.4, 150.0], [31.5, 150.0], [31.6, 152.0], [31.7, 152.0], [31.8, 152.0], [31.9, 152.0], [32.0, 152.0], [32.1, 152.0], [32.2, 152.0], [32.3, 152.0], [32.4, 154.0], [32.5, 154.0], [32.6, 154.0], [32.7, 154.0], [32.8, 154.0], [32.9, 154.0], [33.0, 154.0], [33.1, 154.0], [33.2, 154.0], [33.3, 154.0], [33.4, 154.0], [33.5, 154.0], [33.6, 155.0], [33.7, 155.0], [33.8, 155.0], [33.9, 155.0], [34.0, 155.0], [34.1, 155.0], [34.2, 155.0], [34.3, 155.0], [34.4, 158.0], [34.5, 158.0], [34.6, 158.0], [34.7, 158.0], [34.8, 160.0], [34.9, 160.0], [35.0, 160.0], [35.1, 160.0], [35.2, 161.0], [35.3, 161.0], [35.4, 161.0], [35.5, 161.0], [35.6, 162.0], [35.7, 162.0], [35.8, 162.0], [35.9, 162.0], [36.0, 163.0], [36.1, 163.0], [36.2, 163.0], [36.3, 163.0], [36.4, 163.0], [36.5, 163.0], [36.6, 163.0], [36.7, 163.0], [36.8, 163.0], [36.9, 163.0], [37.0, 163.0], [37.1, 163.0], [37.2, 164.0], [37.3, 164.0], [37.4, 164.0], [37.5, 164.0], [37.6, 166.0], [37.7, 166.0], [37.8, 166.0], [37.9, 166.0], [38.0, 171.0], [38.1, 171.0], [38.2, 171.0], [38.3, 171.0], [38.4, 172.0], [38.5, 172.0], [38.6, 172.0], [38.7, 172.0], [38.8, 173.0], [38.9, 173.0], [39.0, 173.0], [39.1, 173.0], [39.2, 173.0], [39.3, 173.0], [39.4, 173.0], [39.5, 173.0], [39.6, 174.0], [39.7, 174.0], [39.8, 174.0], [39.9, 174.0], [40.0, 177.0], [40.1, 177.0], [40.2, 177.0], [40.3, 177.0], [40.4, 177.0], [40.5, 177.0], [40.6, 177.0], [40.7, 177.0], [40.8, 178.0], [40.9, 178.0], [41.0, 178.0], [41.1, 178.0], [41.2, 185.0], [41.3, 185.0], [41.4, 185.0], [41.5, 185.0], [41.6, 186.0], [41.7, 186.0], [41.8, 186.0], [41.9, 186.0], [42.0, 186.0], [42.1, 186.0], [42.2, 186.0], [42.3, 186.0], [42.4, 189.0], [42.5, 189.0], [42.6, 189.0], [42.7, 189.0], [42.8, 192.0], [42.9, 192.0], [43.0, 192.0], [43.1, 192.0], [43.2, 192.0], [43.3, 192.0], [43.4, 192.0], [43.5, 192.0], [43.6, 196.0], [43.7, 196.0], [43.8, 196.0], [43.9, 196.0], [44.0, 198.0], [44.1, 198.0], [44.2, 198.0], [44.3, 198.0], [44.4, 213.0], [44.5, 213.0], [44.6, 213.0], [44.7, 213.0], [44.8, 214.0], [44.9, 214.0], [45.0, 214.0], [45.1, 214.0], [45.2, 215.0], [45.3, 215.0], [45.4, 215.0], [45.5, 215.0], [45.6, 217.0], [45.7, 217.0], [45.8, 217.0], [45.9, 217.0], [46.0, 217.0], [46.1, 217.0], [46.2, 217.0], [46.3, 217.0], [46.4, 223.0], [46.5, 223.0], [46.6, 223.0], [46.7, 223.0], [46.8, 224.0], [46.9, 224.0], [47.0, 224.0], [47.1, 224.0], [47.2, 225.0], [47.3, 225.0], [47.4, 225.0], [47.5, 225.0], [47.6, 227.0], [47.7, 227.0], [47.8, 227.0], [47.9, 227.0], [48.0, 230.0], [48.1, 230.0], [48.2, 230.0], [48.3, 230.0], [48.4, 231.0], [48.5, 231.0], [48.6, 231.0], [48.7, 231.0], [48.8, 232.0], [48.9, 232.0], [49.0, 232.0], [49.1, 232.0], [49.2, 234.0], [49.3, 234.0], [49.4, 234.0], [49.5, 234.0], [49.6, 236.0], [49.7, 236.0], [49.8, 236.0], [49.9, 236.0], [50.0, 239.0], [50.1, 239.0], [50.2, 239.0], [50.3, 239.0], [50.4, 242.0], [50.5, 242.0], [50.6, 242.0], [50.7, 242.0], [50.8, 243.0], [50.9, 243.0], [51.0, 243.0], [51.1, 243.0], [51.2, 246.0], [51.3, 246.0], [51.4, 246.0], [51.5, 246.0], [51.6, 249.0], [51.7, 249.0], [51.8, 249.0], [51.9, 249.0], [52.0, 251.0], [52.1, 251.0], [52.2, 251.0], [52.3, 251.0], [52.4, 258.0], [52.5, 258.0], [52.6, 258.0], [52.7, 258.0], [52.8, 264.0], [52.9, 264.0], [53.0, 264.0], [53.1, 264.0], [53.2, 265.0], [53.3, 265.0], [53.4, 265.0], [53.5, 265.0], [53.6, 275.0], [53.7, 275.0], [53.8, 275.0], [53.9, 275.0], [54.0, 283.0], [54.1, 283.0], [54.2, 283.0], [54.3, 283.0], [54.4, 284.0], [54.5, 284.0], [54.6, 284.0], [54.7, 284.0], [54.8, 286.0], [54.9, 286.0], [55.0, 286.0], [55.1, 286.0], [55.2, 288.0], [55.3, 288.0], [55.4, 288.0], [55.5, 288.0], [55.6, 288.0], [55.7, 288.0], [55.8, 288.0], [55.9, 288.0], [56.0, 300.0], [56.1, 300.0], [56.2, 300.0], [56.3, 300.0], [56.4, 305.0], [56.5, 305.0], [56.6, 305.0], [56.7, 305.0], [56.8, 312.0], [56.9, 312.0], [57.0, 312.0], [57.1, 312.0], [57.2, 312.0], [57.3, 312.0], [57.4, 312.0], [57.5, 312.0], [57.6, 316.0], [57.7, 316.0], [57.8, 316.0], [57.9, 316.0], [58.0, 317.0], [58.1, 317.0], [58.2, 317.0], [58.3, 317.0], [58.4, 319.0], [58.5, 319.0], [58.6, 319.0], [58.7, 319.0], [58.8, 320.0], [58.9, 320.0], [59.0, 320.0], [59.1, 320.0], [59.2, 321.0], [59.3, 321.0], [59.4, 321.0], [59.5, 321.0], [59.6, 322.0], [59.7, 322.0], [59.8, 322.0], [59.9, 322.0], [60.0, 334.0], [60.1, 334.0], [60.2, 334.0], [60.3, 334.0], [60.4, 335.0], [60.5, 335.0], [60.6, 335.0], [60.7, 335.0], [60.8, 335.0], [60.9, 335.0], [61.0, 335.0], [61.1, 335.0], [61.2, 336.0], [61.3, 336.0], [61.4, 336.0], [61.5, 336.0], [61.6, 341.0], [61.7, 341.0], [61.8, 341.0], [61.9, 341.0], [62.0, 343.0], [62.1, 343.0], [62.2, 343.0], [62.3, 343.0], [62.4, 344.0], [62.5, 344.0], [62.6, 344.0], [62.7, 344.0], [62.8, 345.0], [62.9, 345.0], [63.0, 345.0], [63.1, 345.0], [63.2, 349.0], [63.3, 349.0], [63.4, 349.0], [63.5, 349.0], [63.6, 350.0], [63.7, 350.0], [63.8, 350.0], [63.9, 350.0], [64.0, 353.0], [64.1, 353.0], [64.2, 353.0], [64.3, 353.0], [64.4, 354.0], [64.5, 354.0], [64.6, 354.0], [64.7, 354.0], [64.8, 360.0], [64.9, 360.0], [65.0, 360.0], [65.1, 360.0], [65.2, 364.0], [65.3, 364.0], [65.4, 364.0], [65.5, 364.0], [65.6, 365.0], [65.7, 365.0], [65.8, 365.0], [65.9, 365.0], [66.0, 365.0], [66.1, 365.0], [66.2, 365.0], [66.3, 365.0], [66.4, 365.0], [66.5, 365.0], [66.6, 365.0], [66.7, 365.0], [66.8, 367.0], [66.9, 367.0], [67.0, 367.0], [67.1, 367.0], [67.2, 368.0], [67.3, 368.0], [67.4, 368.0], [67.5, 368.0], [67.6, 369.0], [67.7, 369.0], [67.8, 369.0], [67.9, 369.0], [68.0, 370.0], [68.1, 370.0], [68.2, 370.0], [68.3, 370.0], [68.4, 371.0], [68.5, 371.0], [68.6, 371.0], [68.7, 371.0], [68.8, 372.0], [68.9, 372.0], [69.0, 372.0], [69.1, 372.0], [69.2, 373.0], [69.3, 373.0], [69.4, 373.0], [69.5, 373.0], [69.6, 375.0], [69.7, 375.0], [69.8, 375.0], [69.9, 375.0], [70.0, 375.0], [70.1, 375.0], [70.2, 375.0], [70.3, 375.0], [70.4, 376.0], [70.5, 376.0], [70.6, 376.0], [70.7, 376.0], [70.8, 377.0], [70.9, 377.0], [71.0, 377.0], [71.1, 377.0], [71.2, 378.0], [71.3, 378.0], [71.4, 378.0], [71.5, 378.0], [71.6, 384.0], [71.7, 384.0], [71.8, 384.0], [71.9, 384.0], [72.0, 387.0], [72.1, 387.0], [72.2, 387.0], [72.3, 387.0], [72.4, 388.0], [72.5, 388.0], [72.6, 388.0], [72.7, 388.0], [72.8, 389.0], [72.9, 389.0], [73.0, 389.0], [73.1, 389.0], [73.2, 389.0], [73.3, 389.0], [73.4, 389.0], [73.5, 389.0], [73.6, 390.0], [73.7, 390.0], [73.8, 390.0], [73.9, 390.0], [74.0, 391.0], [74.1, 391.0], [74.2, 391.0], [74.3, 391.0], [74.4, 394.0], [74.5, 394.0], [74.6, 394.0], [74.7, 394.0], [74.8, 396.0], [74.9, 396.0], [75.0, 396.0], [75.1, 396.0], [75.2, 399.0], [75.3, 399.0], [75.4, 399.0], [75.5, 399.0], [75.6, 399.0], [75.7, 401.0], [75.8, 401.0], [75.9, 401.0], [76.0, 401.0], [76.1, 405.0], [76.2, 405.0], [76.3, 405.0], [76.4, 405.0], [76.5, 406.0], [76.6, 406.0], [76.7, 406.0], [76.8, 406.0], [76.9, 406.0], [77.0, 406.0], [77.1, 406.0], [77.2, 406.0], [77.3, 411.0], [77.4, 411.0], [77.5, 411.0], [77.6, 411.0], [77.7, 412.0], [77.8, 412.0], [77.9, 412.0], [78.0, 412.0], [78.1, 413.0], [78.2, 413.0], [78.3, 413.0], [78.4, 413.0], [78.5, 415.0], [78.6, 415.0], [78.7, 415.0], [78.8, 415.0], [78.9, 417.0], [79.0, 417.0], [79.1, 417.0], [79.2, 417.0], [79.3, 420.0], [79.4, 420.0], [79.5, 420.0], [79.6, 420.0], [79.7, 421.0], [79.8, 421.0], [79.9, 421.0], [80.0, 421.0], [80.1, 424.0], [80.2, 424.0], [80.3, 424.0], [80.4, 424.0], [80.5, 425.0], [80.6, 425.0], [80.7, 425.0], [80.8, 425.0], [80.9, 425.0], [81.0, 425.0], [81.1, 425.0], [81.2, 425.0], [81.3, 425.0], [81.4, 425.0], [81.5, 425.0], [81.6, 425.0], [81.7, 427.0], [81.8, 427.0], [81.9, 427.0], [82.0, 427.0], [82.1, 431.0], [82.2, 431.0], [82.3, 431.0], [82.4, 431.0], [82.5, 444.0], [82.6, 444.0], [82.7, 444.0], [82.8, 444.0], [82.9, 449.0], [83.0, 449.0], [83.1, 449.0], [83.2, 449.0], [83.3, 454.0], [83.4, 454.0], [83.5, 454.0], [83.6, 454.0], [83.7, 458.0], [83.8, 458.0], [83.9, 458.0], [84.0, 458.0], [84.1, 463.0], [84.2, 463.0], [84.3, 463.0], [84.4, 463.0], [84.5, 467.0], [84.6, 467.0], [84.7, 467.0], [84.8, 467.0], [84.9, 475.0], [85.0, 475.0], [85.1, 475.0], [85.2, 475.0], [85.3, 478.0], [85.4, 478.0], [85.5, 478.0], [85.6, 478.0], [85.7, 478.0], [85.8, 478.0], [85.9, 478.0], [86.0, 478.0], [86.1, 481.0], [86.2, 481.0], [86.3, 481.0], [86.4, 481.0], [86.5, 486.0], [86.6, 486.0], [86.7, 486.0], [86.8, 486.0], [86.9, 488.0], [87.0, 488.0], [87.1, 488.0], [87.2, 488.0], [87.3, 496.0], [87.4, 496.0], [87.5, 496.0], [87.6, 496.0], [87.7, 507.0], [87.8, 507.0], [87.9, 507.0], [88.0, 507.0], [88.1, 519.0], [88.2, 519.0], [88.3, 519.0], [88.4, 519.0], [88.5, 522.0], [88.6, 522.0], [88.7, 522.0], [88.8, 522.0], [88.9, 523.0], [89.0, 523.0], [89.1, 523.0], [89.2, 523.0], [89.3, 525.0], [89.4, 525.0], [89.5, 525.0], [89.6, 525.0], [89.7, 552.0], [89.8, 552.0], [89.9, 552.0], [90.0, 552.0], [90.1, 554.0], [90.2, 554.0], [90.3, 554.0], [90.4, 554.0], [90.5, 560.0], [90.6, 560.0], [90.7, 560.0], [90.8, 560.0], [90.9, 610.0], [91.0, 610.0], [91.1, 610.0], [91.2, 610.0], [91.3, 615.0], [91.4, 615.0], [91.5, 615.0], [91.6, 615.0], [91.7, 625.0], [91.8, 625.0], [91.9, 625.0], [92.0, 625.0], [92.1, 631.0], [92.2, 631.0], [92.3, 631.0], [92.4, 631.0], [92.5, 635.0], [92.6, 635.0], [92.7, 635.0], [92.8, 635.0], [92.9, 640.0], [93.0, 640.0], [93.1, 640.0], [93.2, 640.0], [93.3, 646.0], [93.4, 646.0], [93.5, 646.0], [93.6, 646.0], [93.7, 652.0], [93.8, 652.0], [93.9, 652.0], [94.0, 652.0], [94.1, 657.0], [94.2, 657.0], [94.3, 657.0], [94.4, 657.0], [94.5, 659.0], [94.6, 659.0], [94.7, 659.0], [94.8, 659.0], [94.9, 668.0], [95.0, 668.0], [95.1, 668.0], [95.2, 668.0], [95.3, 671.0], [95.4, 671.0], [95.5, 671.0], [95.6, 671.0], [95.7, 677.0], [95.8, 677.0], [95.9, 677.0], [96.0, 677.0], [96.1, 678.0], [96.2, 678.0], [96.3, 678.0], [96.4, 678.0], [96.5, 682.0], [96.6, 682.0], [96.7, 682.0], [96.8, 682.0], [96.9, 705.0], [97.0, 705.0], [97.1, 705.0], [97.2, 705.0], [97.3, 711.0], [97.4, 711.0], [97.5, 711.0], [97.6, 711.0], [97.7, 721.0], [97.8, 721.0], [97.9, 721.0], [98.0, 721.0], [98.1, 762.0], [98.2, 762.0], [98.3, 762.0], [98.4, 762.0], [98.5, 766.0], [98.6, 766.0], [98.7, 766.0], [98.8, 766.0], [98.9, 779.0], [99.0, 779.0], [99.1, 779.0], [99.2, 779.0], [99.3, 983.0], [99.4, 983.0], [99.5, 983.0], [99.6, 983.0], [99.7, 997.0], [99.8, 997.0], [99.9, 997.0], [100.0, 997.0]], "isOverall": false, "label": "Create Payment API One", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 100.0, "title": "Response Time Percentiles"}},
        getOptions: function() {
            return {
                series: {
                    points: { show: false }
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentiles'
                },
                xaxis: {
                    tickDecimals: 1,
                    axisLabel: "Percentiles",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Percentile value in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : %x.2 percentile was %y ms"
                },
                selection: { mode: "xy" },
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentiles"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesPercentiles"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesPercentiles"), dataset, prepareOverviewOptions(options));
        }
};

/**
 * @param elementId Id of element where we display message
 */
function setEmptyGraph(elementId) {
    $(function() {
        $(elementId).text("No graph series with filter="+seriesFilter);
    });
}

// Response times percentiles
function refreshResponseTimePercentiles() {
    var infos = responseTimePercentilesInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimePercentiles");
        return;
    }
    if (isGraph($("#flotResponseTimesPercentiles"))){
        infos.createGraph();
    } else {
        var choiceContainer = $("#choicesResponseTimePercentiles");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesPercentiles", "#overviewResponseTimesPercentiles");
        $('#bodyResponseTimePercentiles .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimeDistributionInfos = {
        data: {"result": {"minY": 2.0, "minX": 0.0, "maxY": 71.0, "series": [{"data": [[0.0, 40.0], [300.0, 49.0], [600.0, 15.0], [700.0, 6.0], [100.0, 71.0], [200.0, 29.0], [400.0, 30.0], [900.0, 2.0], [500.0, 8.0]], "isOverall": false, "label": "Create Payment API One", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 100, "maxX": 900.0, "title": "Response Time Distribution"}},
        getOptions: function() {
            var granularity = this.data.result.granularity;
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    barWidth: this.data.result.granularity
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " responses for " + label + " were between " + xval + " and " + (xval + granularity) + " ms";
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimeDistribution"), prepareData(data.result.series, $("#choicesResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshResponseTimeDistribution() {
    var infos = responseTimeDistributionInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeDistribution");
        return;
    }
    if (isGraph($("#flotResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var syntheticResponseTimeDistributionInfos = {
        data: {"result": {"minY": 31.0, "minX": 0.0, "ticks": [[0, "Requests having \nresponse time <= 500ms"], [1, "Requests having \nresponse time > 500ms and <= 1,500ms"], [2, "Requests having \nresponse time > 1,500ms"], [3, "Requests in error"]], "maxY": 219.0, "series": [{"data": [[0.0, 219.0]], "color": "#9ACD32", "isOverall": false, "label": "Requests having \nresponse time <= 500ms", "isController": false}, {"data": [[1.0, 31.0]], "color": "yellow", "isOverall": false, "label": "Requests having \nresponse time > 500ms and <= 1,500ms", "isController": false}, {"data": [], "color": "orange", "isOverall": false, "label": "Requests having \nresponse time > 1,500ms", "isController": false}, {"data": [], "color": "#FF6347", "isOverall": false, "label": "Requests in error", "isController": false}], "supportsControllersDiscrimination": false, "maxX": 1.0, "title": "Synthetic Response Times Distribution"}},
        getOptions: function() {
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendSyntheticResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times ranges",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                    tickLength:0,
                    min:-0.5,
                    max:3.5
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    align: "center",
                    barWidth: 0.25,
                    fill:.75
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " " + label;
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            options.xaxis.ticks = data.result.ticks;
            $.plot($("#flotSyntheticResponseTimeDistribution"), prepareData(data.result.series, $("#choicesSyntheticResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshSyntheticResponseTimeDistribution() {
    var infos = syntheticResponseTimeDistributionInfos;
    prepareSeries(infos.data, true);
    if (isGraph($("#flotSyntheticResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerSyntheticResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var activeThreadsOverTimeInfos = {
        data: {"result": {"minY": 32.836000000000034, "minX": 1.77968646E12, "maxY": 32.836000000000034, "series": [{"data": [[1.77968646E12, 32.836000000000034]], "isOverall": false, "label": "250 TPS Thread Group", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77968646E12, "title": "Active Threads Over Time"}},
        getOptions: function() {
            return {
                series: {
                    stack: true,
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 6,
                    show: true,
                    container: '#legendActiveThreadsOverTime'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                selection: {
                    mode: 'xy'
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : At %x there were %y active threads"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesActiveThreadsOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotActiveThreadsOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewActiveThreadsOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Active Threads Over Time
function refreshActiveThreadsOverTime(fixTimestamps) {
    var infos = activeThreadsOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 19800000);
    }
    if(isGraph($("#flotActiveThreadsOverTime"))) {
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesActiveThreadsOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotActiveThreadsOverTime", "#overviewActiveThreadsOverTime");
        $('#footerActiveThreadsOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var timeVsThreadsInfos = {
        data: {"result": {"minY": 36.0, "minX": 1.0, "maxY": 678.0, "series": [{"data": [[2.0, 68.0], [3.0, 56.0], [4.0, 60.0], [5.0, 69.0], [6.0, 94.0], [7.0, 90.0], [8.0, 88.0], [9.0, 91.5], [10.0, 87.0], [11.0, 90.5], [12.0, 83.0], [13.0, 112.0], [14.0, 92.0], [15.0, 114.0], [16.0, 114.5], [17.0, 149.74999999999997], [18.0, 151.25], [19.0, 127.2], [20.0, 117.5], [21.0, 155.33333333333334], [22.0, 159.2222222222222], [23.0, 142.4], [24.0, 173.5], [25.0, 368.09090909090907], [26.0, 213.33333333333331], [27.0, 178.66666666666666], [28.0, 237.5], [29.0, 255.66666666666666], [30.0, 171.66666666666666], [31.0, 489.0], [32.0, 220.66666666666666], [33.0, 413.0], [34.0, 538.2857142857143], [35.0, 384.8333333333333], [36.0, 319.5714285714286], [37.0, 383.75], [38.0, 337.7142857142857], [39.0, 363.4666666666667], [41.0, 398.7692307692307], [40.0, 678.0], [42.0, 363.0], [43.0, 443.6], [44.0, 281.375], [45.0, 408.43749999999994], [46.0, 345.5], [47.0, 109.5], [48.0, 361.73333333333335], [49.0, 297.3333333333333], [50.0, 303.72222222222223], [1.0, 36.0]], "isOverall": false, "label": "Create Payment API One", "isController": false}, {"data": [[32.836000000000034, 286.132]], "isOverall": false, "label": "Create Payment API One-Aggregated", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 50.0, "title": "Time VS Threads"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: { noColumns: 2,show: true, container: '#legendTimeVsThreads' },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s: At %x.2 active threads, Average response time was %y.2 ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesTimeVsThreads"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotTimesVsThreads"), dataset, options);
            // setup overview
            $.plot($("#overviewTimesVsThreads"), dataset, prepareOverviewOptions(options));
        }
};

// Time vs threads
function refreshTimeVsThreads(){
    var infos = timeVsThreadsInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTimeVsThreads");
        return;
    }
    if(isGraph($("#flotTimesVsThreads"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTimeVsThreads");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTimesVsThreads", "#overviewTimesVsThreads");
        $('#footerTimeVsThreads .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var bytesThroughputOverTimeInfos = {
        data : {"result": {"minY": 1016.6666666666666, "minX": 1.77968646E12, "maxY": 1708.3333333333333, "series": [{"data": [[1.77968646E12, 1016.6666666666666]], "isOverall": false, "label": "Bytes received per second", "isController": false}, {"data": [[1.77968646E12, 1708.3333333333333]], "isOverall": false, "label": "Bytes sent per second", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77968646E12, "title": "Bytes Throughput Over Time"}},
        getOptions : function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity) ,
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Bytes / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendBytesThroughputOverTime'
                },
                selection: {
                    mode: "xy"
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y"
                }
            };
        },
        createGraph : function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesBytesThroughputOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotBytesThroughputOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewBytesThroughputOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Bytes throughput Over Time
function refreshBytesThroughputOverTime(fixTimestamps) {
    var infos = bytesThroughputOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 19800000);
    }
    if(isGraph($("#flotBytesThroughputOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesBytesThroughputOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotBytesThroughputOverTime", "#overviewBytesThroughputOverTime");
        $('#footerBytesThroughputOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimesOverTimeInfos = {
        data: {"result": {"minY": 286.132, "minX": 1.77968646E12, "maxY": 286.132, "series": [{"data": [[1.77968646E12, 286.132]], "isOverall": false, "label": "Create Payment API One", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77968646E12, "title": "Response Time Over Time"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average response time was %y ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Times Over Time
function refreshResponseTimeOverTime(fixTimestamps) {
    var infos = responseTimesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 19800000);
    }
    if(isGraph($("#flotResponseTimesOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesOverTime", "#overviewResponseTimesOverTime");
        $('#footerResponseTimesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var latenciesOverTimeInfos = {
        data: {"result": {"minY": 284.376, "minX": 1.77968646E12, "maxY": 284.376, "series": [{"data": [[1.77968646E12, 284.376]], "isOverall": false, "label": "Create Payment API One", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77968646E12, "title": "Latencies Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response latencies in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendLatenciesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average latency was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesLatenciesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotLatenciesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewLatenciesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Latencies Over Time
function refreshLatenciesOverTime(fixTimestamps) {
    var infos = latenciesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyLatenciesOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 19800000);
    }
    if(isGraph($("#flotLatenciesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesLatenciesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotLatenciesOverTime", "#overviewLatenciesOverTime");
        $('#footerLatenciesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var connectTimeOverTimeInfos = {
        data: {"result": {"minY": 2.7040000000000037, "minX": 1.77968646E12, "maxY": 2.7040000000000037, "series": [{"data": [[1.77968646E12, 2.7040000000000037]], "isOverall": false, "label": "Create Payment API One", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77968646E12, "title": "Connect Time Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getConnectTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average Connect Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendConnectTimeOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average connect time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesConnectTimeOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotConnectTimeOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewConnectTimeOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Connect Time Over Time
function refreshConnectTimeOverTime(fixTimestamps) {
    var infos = connectTimeOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyConnectTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 19800000);
    }
    if(isGraph($("#flotConnectTimeOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesConnectTimeOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotConnectTimeOverTime", "#overviewConnectTimeOverTime");
        $('#footerConnectTimeOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var responseTimePercentilesOverTimeInfos = {
        data: {"result": {"minY": 36.0, "minX": 1.77968646E12, "maxY": 997.0, "series": [{"data": [[1.77968646E12, 997.0]], "isOverall": false, "label": "Max", "isController": false}, {"data": [[1.77968646E12, 36.0]], "isOverall": false, "label": "Min", "isController": false}, {"data": [[1.77968646E12, 553.8]], "isOverall": false, "label": "90th percentile", "isController": false}, {"data": [[1.77968646E12, 878.9600000000019]], "isOverall": false, "label": "99th percentile", "isController": false}, {"data": [[1.77968646E12, 237.5]], "isOverall": false, "label": "Median", "isController": false}, {"data": [[1.77968646E12, 669.3499999999999]], "isOverall": false, "label": "95th percentile", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77968646E12, "title": "Response Time Percentiles Over Time (successful requests only)"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Response Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentilesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Response time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentilesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimePercentilesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimePercentilesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Time Percentiles Over Time
function refreshResponseTimePercentilesOverTime(fixTimestamps) {
    var infos = responseTimePercentilesOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 19800000);
    }
    if(isGraph($("#flotResponseTimePercentilesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimePercentilesOverTime", "#overviewResponseTimePercentilesOverTime");
        $('#footerResponseTimePercentilesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var responseTimeVsRequestInfos = {
    data: {"result": {"minY": 58.0, "minX": 4.0, "maxY": 335.0, "series": [{"data": [[4.0, 58.0], [137.0, 335.0], [109.0, 192.0]], "isOverall": false, "label": "Successes", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 137.0, "title": "Response Time Vs Request"}},
    getOptions: function() {
        return {
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Response Time in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: {
                noColumns: 2,
                show: true,
                container: '#legendResponseTimeVsRequest'
            },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median response time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesResponseTimeVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotResponseTimeVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewResponseTimeVsRequest"), dataset, prepareOverviewOptions(options));

    }
};

// Response Time vs Request
function refreshResponseTimeVsRequest() {
    var infos = responseTimeVsRequestInfos;
    prepareSeries(infos.data);
    if (isGraph($("#flotResponseTimeVsRequest"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeVsRequest");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimeVsRequest", "#overviewResponseTimeVsRequest");
        $('#footerResponseRimeVsRequest .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var latenciesVsRequestInfos = {
    data: {"result": {"minY": 57.5, "minX": 4.0, "maxY": 334.0, "series": [{"data": [[4.0, 57.5], [137.0, 334.0], [109.0, 192.0]], "isOverall": false, "label": "Successes", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 137.0, "title": "Latencies Vs Request"}},
    getOptions: function() {
        return{
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Latency in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: { noColumns: 2,show: true, container: '#legendLatencyVsRequest' },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median Latency time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesLatencyVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotLatenciesVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewLatenciesVsRequest"), dataset, prepareOverviewOptions(options));
    }
};

// Latencies vs Request
function refreshLatenciesVsRequest() {
        var infos = latenciesVsRequestInfos;
        prepareSeries(infos.data);
        if(isGraph($("#flotLatenciesVsRequest"))){
            infos.createGraph();
        }else{
            var choiceContainer = $("#choicesLatencyVsRequest");
            createLegend(choiceContainer, infos);
            infos.createGraph();
            setGraphZoomable("#flotLatenciesVsRequest", "#overviewLatenciesVsRequest");
            $('#footerLatenciesVsRequest .legendColorBox > div').each(function(i){
                $(this).clone().prependTo(choiceContainer.find("li").eq(i));
            });
        }
};

var hitsPerSecondInfos = {
        data: {"result": {"minY": 4.166666666666667, "minX": 1.77968646E12, "maxY": 4.166666666666667, "series": [{"data": [[1.77968646E12, 4.166666666666667]], "isOverall": false, "label": "hitsPerSecond", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77968646E12, "title": "Hits Per Second"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of hits / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendHitsPerSecond"
                },
                selection: {
                    mode : 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y.2 hits/sec"
                }
            };
        },
        createGraph: function createGraph() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesHitsPerSecond"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotHitsPerSecond"), dataset, options);
            // setup overview
            $.plot($("#overviewHitsPerSecond"), dataset, prepareOverviewOptions(options));
        }
};

// Hits per second
function refreshHitsPerSecond(fixTimestamps) {
    var infos = hitsPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 19800000);
    }
    if (isGraph($("#flotHitsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesHitsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotHitsPerSecond", "#overviewHitsPerSecond");
        $('#footerHitsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var codesPerSecondInfos = {
        data: {"result": {"minY": 4.166666666666667, "minX": 1.77968646E12, "maxY": 4.166666666666667, "series": [{"data": [[1.77968646E12, 4.166666666666667]], "isOverall": false, "label": "201", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77968646E12, "title": "Codes Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendCodesPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "Number of Response Codes %s at %x was %y.2 responses / sec"
                }
            };
        },
    createGraph: function() {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesCodesPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotCodesPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewCodesPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Codes per second
function refreshCodesPerSecond(fixTimestamps) {
    var infos = codesPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 19800000);
    }
    if(isGraph($("#flotCodesPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesCodesPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotCodesPerSecond", "#overviewCodesPerSecond");
        $('#footerCodesPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var transactionsPerSecondInfos = {
        data: {"result": {"minY": 4.166666666666667, "minX": 1.77968646E12, "maxY": 4.166666666666667, "series": [{"data": [[1.77968646E12, 4.166666666666667]], "isOverall": false, "label": "Create Payment API One-success", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77968646E12, "title": "Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTransactionsPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                }
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTransactionsPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTransactionsPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewTransactionsPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Transactions per second
function refreshTransactionsPerSecond(fixTimestamps) {
    var infos = transactionsPerSecondInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTransactionsPerSecond");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 19800000);
    }
    if(isGraph($("#flotTransactionsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTransactionsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTransactionsPerSecond", "#overviewTransactionsPerSecond");
        $('#footerTransactionsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var totalTPSInfos = {
        data: {"result": {"minY": 4.166666666666667, "minX": 1.77968646E12, "maxY": 4.166666666666667, "series": [{"data": [[1.77968646E12, 4.166666666666667]], "isOverall": false, "label": "Transaction-success", "isController": false}, {"data": [], "isOverall": false, "label": "Transaction-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77968646E12, "title": "Total Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTotalTPS"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                },
                colors: ["#9ACD32", "#FF6347"]
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTotalTPS"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTotalTPS"), dataset, options);
        // setup overview
        $.plot($("#overviewTotalTPS"), dataset, prepareOverviewOptions(options));
    }
};

// Total Transactions per second
function refreshTotalTPS(fixTimestamps) {
    var infos = totalTPSInfos;
    // We want to ignore seriesFilter
    prepareSeries(infos.data, false, true);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 19800000);
    }
    if(isGraph($("#flotTotalTPS"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTotalTPS");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTotalTPS", "#overviewTotalTPS");
        $('#footerTotalTPS .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

// Collapse the graph matching the specified DOM element depending the collapsed
// status
function collapse(elem, collapsed){
    if(collapsed){
        $(elem).parent().find(".fa-chevron-up").removeClass("fa-chevron-up").addClass("fa-chevron-down");
    } else {
        $(elem).parent().find(".fa-chevron-down").removeClass("fa-chevron-down").addClass("fa-chevron-up");
        if (elem.id == "bodyBytesThroughputOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshBytesThroughputOverTime(true);
            }
            document.location.href="#bytesThroughputOverTime";
        } else if (elem.id == "bodyLatenciesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesOverTime(true);
            }
            document.location.href="#latenciesOverTime";
        } else if (elem.id == "bodyCustomGraph") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCustomGraph(true);
            }
            document.location.href="#responseCustomGraph";
        } else if (elem.id == "bodyConnectTimeOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshConnectTimeOverTime(true);
            }
            document.location.href="#connectTimeOverTime";
        } else if (elem.id == "bodyResponseTimePercentilesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimePercentilesOverTime(true);
            }
            document.location.href="#responseTimePercentilesOverTime";
        } else if (elem.id == "bodyResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeDistribution();
            }
            document.location.href="#responseTimeDistribution" ;
        } else if (elem.id == "bodySyntheticResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshSyntheticResponseTimeDistribution();
            }
            document.location.href="#syntheticResponseTimeDistribution" ;
        } else if (elem.id == "bodyActiveThreadsOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshActiveThreadsOverTime(true);
            }
            document.location.href="#activeThreadsOverTime";
        } else if (elem.id == "bodyTimeVsThreads") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTimeVsThreads();
            }
            document.location.href="#timeVsThreads" ;
        } else if (elem.id == "bodyCodesPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCodesPerSecond(true);
            }
            document.location.href="#codesPerSecond";
        } else if (elem.id == "bodyTransactionsPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTransactionsPerSecond(true);
            }
            document.location.href="#transactionsPerSecond";
        } else if (elem.id == "bodyTotalTPS") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTotalTPS(true);
            }
            document.location.href="#totalTPS";
        } else if (elem.id == "bodyResponseTimeVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeVsRequest();
            }
            document.location.href="#responseTimeVsRequest";
        } else if (elem.id == "bodyLatenciesVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesVsRequest();
            }
            document.location.href="#latencyVsRequest";
        }
    }
}

/*
 * Activates or deactivates all series of the specified graph (represented by id parameter)
 * depending on checked argument.
 */
function toggleAll(id, checked){
    var placeholder = document.getElementById(id);

    var cases = $(placeholder).find(':checkbox');
    cases.prop('checked', checked);
    $(cases).parent().children().children().toggleClass("legend-disabled", !checked);

    var choiceContainer;
    if ( id == "choicesBytesThroughputOverTime"){
        choiceContainer = $("#choicesBytesThroughputOverTime");
        refreshBytesThroughputOverTime(false);
    } else if(id == "choicesResponseTimesOverTime"){
        choiceContainer = $("#choicesResponseTimesOverTime");
        refreshResponseTimeOverTime(false);
    }else if(id == "choicesResponseCustomGraph"){
        choiceContainer = $("#choicesResponseCustomGraph");
        refreshCustomGraph(false);
    } else if ( id == "choicesLatenciesOverTime"){
        choiceContainer = $("#choicesLatenciesOverTime");
        refreshLatenciesOverTime(false);
    } else if ( id == "choicesConnectTimeOverTime"){
        choiceContainer = $("#choicesConnectTimeOverTime");
        refreshConnectTimeOverTime(false);
    } else if ( id == "choicesResponseTimePercentilesOverTime"){
        choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        refreshResponseTimePercentilesOverTime(false);
    } else if ( id == "choicesResponseTimePercentiles"){
        choiceContainer = $("#choicesResponseTimePercentiles");
        refreshResponseTimePercentiles();
    } else if(id == "choicesActiveThreadsOverTime"){
        choiceContainer = $("#choicesActiveThreadsOverTime");
        refreshActiveThreadsOverTime(false);
    } else if ( id == "choicesTimeVsThreads"){
        choiceContainer = $("#choicesTimeVsThreads");
        refreshTimeVsThreads();
    } else if ( id == "choicesSyntheticResponseTimeDistribution"){
        choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        refreshSyntheticResponseTimeDistribution();
    } else if ( id == "choicesResponseTimeDistribution"){
        choiceContainer = $("#choicesResponseTimeDistribution");
        refreshResponseTimeDistribution();
    } else if ( id == "choicesHitsPerSecond"){
        choiceContainer = $("#choicesHitsPerSecond");
        refreshHitsPerSecond(false);
    } else if(id == "choicesCodesPerSecond"){
        choiceContainer = $("#choicesCodesPerSecond");
        refreshCodesPerSecond(false);
    } else if ( id == "choicesTransactionsPerSecond"){
        choiceContainer = $("#choicesTransactionsPerSecond");
        refreshTransactionsPerSecond(false);
    } else if ( id == "choicesTotalTPS"){
        choiceContainer = $("#choicesTotalTPS");
        refreshTotalTPS(false);
    } else if ( id == "choicesResponseTimeVsRequest"){
        choiceContainer = $("#choicesResponseTimeVsRequest");
        refreshResponseTimeVsRequest();
    } else if ( id == "choicesLatencyVsRequest"){
        choiceContainer = $("#choicesLatencyVsRequest");
        refreshLatenciesVsRequest();
    }
    var color = checked ? "black" : "#818181";
    if(choiceContainer != null) {
        choiceContainer.find("label").each(function(){
            this.style.color = color;
        });
    }
}

