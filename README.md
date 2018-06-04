# ChipView

![API](https://img.shields.io/badge/API-16%2B-34bf49.svg)
[![JitPack](https://jitpack.io/v/jahirfiquitiva/ChipView.svg)](https://jitpack.io/#jahirfiquitiva/ChipView)
[![Build Status](https://travis-ci.com/jahirfiquitiva/ChipView.svg?branch=master)](https://travis-ci.com/jahirfiquitiva/ChipView)

Yep, another Material Chip view library. Just because the Android Support library v 28.x is still in alpha and because no other chip library has been able to provide the simplicity I need (regardless of all their features).

You can download a [sample app here](http://j.mp/ChipViewSample).

---

# Preview

<p>
<img src="https://github.com/jahirfiquitiva/ChipView/raw/master/preview.png" height="400"/>
</p>

---

# Including in your project
Frames is available via JitPack, so getting it as simple as adding it as a dependency, like this:

1. Add JitPack repository to your root `build.gradle` file
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
2. Add the dependency in your project `build.gradle` file
```gradle
dependencies {
    compile('com.jahirfiquitiva:ChipView:{latest version}@aar') {
        transitive = true
    }
}
```
where `{latest version}` corresponds to published version in   [![JitPack](https://jitpack.io/v/jahirfiquitiva/ChipView.svg)](https://jitpack.io/#jahirfiquitiva/ChipView)


## How to implement
:page_with_curl: You can check the **[sample App](https://github.com/jahirfiquitiva/ChipView/tree/master/app/)** for details

---

# Developed by

### [Jahir Fiquitiva](https://jahirfiquitiva.com/)

[![Follow on GitHub](https://img.shields.io/github/followers/jahirfiquitiva.svg?style=social&label=Follow)](https://github.com/jahirfiquitiva)
[![Twitter Follow](https://img.shields.io/twitter/follow/jahirfiquitiva.svg?style=social)](https://twitter.com/jahirfiquitiva)
[![Google+](https://img.shields.io/badge/Follow-Google%2B-ea4335.svg)](https://plus.google.com/+JahirFiquitivaR)

If you found this app/library helpful and want to thank me, you can:

<a target="_blank" href="https://jahirfiquitiva.com/donate/">
<img src="https://jahirfiquitiva.com/share/support_my_work.svg?maxAge=432000" width="200"/>
</a>

**Thanks in advance!** :pray:

---

# License

This app is shared under the CreativeCommons Attribution-ShareAlike license.

	Copyright © 2018 Jahir Fiquitiva

	Licensed under the CreativeCommons Attribution-ShareAlike
	4.0 International License. You may not use this file except in compliance
	with the License. You may obtain a copy of the License at

	   http://creativecommons.org/licenses/by-sa/4.0/legalcode

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.

