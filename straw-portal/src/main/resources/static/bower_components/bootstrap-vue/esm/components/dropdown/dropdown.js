function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); if (enumerableOnly) symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; }); keys.push.apply(keys, symbols); } return keys; }

function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i] != null ? arguments[i] : {}; if (i % 2) { ownKeys(Object(source), true).forEach(function (key) { _defineProperty(target, key, source[key]); }); } else if (Object.getOwnPropertyDescriptors) { Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)); } else { ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } } return target; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

import Vue from '../../utils/vue';
import { arrayIncludes } from '../../utils/array';
import { getComponentConfig } from '../../utils/config';
import { htmlOrText } from '../../utils/html';
import dropdownMixin from '../../mixins/dropdown';
import idMixin from '../../mixins/id';
import normalizeSlotMixin from '../../mixins/normalize-slot';
import { BButton } from '../button/button'; // --- Constants ---

var NAME = 'BDropdown'; // --- Props ---

export var props = {
  text: {
    // Button label
    type: String,
    default: ''
  },
  html: {
    // Button label
    type: String // default: undefined

  },
  variant: {
    type: String,
    default: function _default() {
      return getComponentConfig(NAME, 'variant');
    }
  },
  size: {
    type: String,
    default: function _default() {
      return getComponentConfig(NAME, 'size');
    }
  },
  block: {
    type: Boolean,
    default: false
  },
  menuClass: {
    type: [String, Array, Object] // default: null

  },
  toggleTag: {
    type: String,
    default: 'button'
  },
  toggleText: {
    // This really should be toggleLabel
    type: String,
    default: function _default() {
      return getComponentConfig(NAME, 'toggleText');
    }
  },
  toggleClass: {
    type: [String, Array, Object] // default: null

  },
  noCaret: {
    type: Boolean,
    default: false
  },
  split: {
    type: Boolean,
    default: false
  },
  splitHref: {
    type: String // default: undefined

  },
  splitTo: {
    type: [String, Object] // default: undefined

  },
  splitVariant: {
    type: String,
    default: function _default() {
      return getComponentConfig(NAME, 'splitVariant');
    }
  },
  splitClass: {
    type: [String, Array, Object] // default: null

  },
  splitButtonType: {
    type: String,
    default: 'button',
    validator: function validator(value) {
      return arrayIncludes(['button', 'submit', 'reset'], value);
    }
  },
  lazy: {
    // If true, only render menu contents when open
    type: Boolean,
    default: false
  },
  role: {
    type: String,
    default: 'menu'
  }
}; // --- Main component ---
// @vue/component

export var BDropdown = /*#__PURE__*/Vue.extend({
  name: NAME,
  mixins: [idMixin, dropdownMixin, normalizeSlotMixin],
  props: props,
  computed: {
    dropdownClasses: function dropdownClasses() {
      var block = this.block,
          split = this.split,
          boundary = this.boundary;
      return [this.directionClass, {
        show: this.visible,
        // The 'btn-group' class is required in `split` mode for button alignment
        // It needs also to be applied when `block` is disabled to allow multiple
        // dropdowns to be aligned one line
        'btn-group': split || !block,
        // When `block` is enabled and we are in `split` mode the 'd-flex' class
        // needs to be applied to allow the buttons to stretch to full width
        'd-flex': block && split,
        // Position `static` is needed to allow menu to "breakout" of the `scrollParent`
        // boundaries when boundary is anything other than `scrollParent`
        // See: https://github.com/twbs/bootstrap/issues/24251#issuecomment-341413786
        'position-static': boundary !== 'scrollParent' || !boundary
      }];
    },
    menuClasses: function menuClasses() {
      return [this.menuClass, {
        'dropdown-menu-right': this.right,
        show: this.visible
      }];
    },
    toggleClasses: function toggleClasses() {
      var split = this.split;
      return [this.toggleClass, {
        'dropdown-toggle-split': split,
        'dropdown-toggle-no-caret': this.noCaret && !split
      }];
    }
  },
  render: function render(h) {
    var variant = this.variant,
        size = this.size,
        block = this.block,
        disabled = this.disabled,
        split = this.split,
        role = this.role;
    var commonProps = {
      variant: variant,
      size: size,
      block: block,
      disabled: disabled
    };
    var $buttonContent = this.normalizeSlot('button-content');
    var buttonContentProps = this.hasNormalizedSlot('button-content') ? {} : htmlOrText(this.html, this.text);
    var $split = h();

    if (split) {
      var splitTo = this.splitTo,
          splitHref = this.splitHref,
          splitButtonType = this.splitButtonType;

      var btnProps = _objectSpread(_objectSpread({}, commonProps), {}, {
        variant: this.splitVariant || this.variant
      }); // We add these as needed due to <router-link> issues with
      // defined property with `undefined`/`null` values


      if (splitTo) {
        btnProps.to = splitTo;
      } else if (splitHref) {
        btnProps.href = splitHref;
      } else if (splitButtonType) {
        btnProps.type = splitButtonType;
      }

      $split = h(BButton, {
        class: this.splitClass,
        attrs: {
          id: this.safeId('_BV_button_')
        },
        props: btnProps,
        domProps: buttonContentProps,
        on: {
          click: this.onSplitClick
        },
        ref: 'button'
      }, [$buttonContent]);
    }

    var $toggle = h(BButton, {
      staticClass: 'dropdown-toggle',
      class: this.toggleClasses,
      attrs: {
        id: this.safeId('_BV_toggle_'),
        'aria-haspopup': 'true',
        'aria-expanded': this.visible ? 'true' : 'false'
      },
      props: _objectSpread(_objectSpread({}, commonProps), {}, {
        tag: this.toggleTag,
        block: block && !split
      }),
      domProps: split ? {} : buttonContentProps,
      on: {
        mousedown: this.onMousedown,
        click: this.toggle,
        keydown: this.toggle // Handle ENTER, SPACE and DOWN

      },
      ref: 'toggle'
    }, [split ? h('span', {
      class: ['sr-only']
    }, [this.toggleText]) : $buttonContent]);
    var $menu = h('ul', {
      staticClass: 'dropdown-menu',
      class: this.menuClasses,
      attrs: {
        role: role,
        tabindex: '-1',
        'aria-labelledby': this.safeId(split ? '_BV_button_' : '_BV_toggle_')
      },
      on: {
        keydown: this.onKeydown // Handle UP, DOWN and ESC

      },
      ref: 'menu'
    }, !this.lazy || this.visible ? this.normalizeSlot('default', {
      hide: this.hide
    }) : [h()]);
    return h('div', {
      staticClass: 'dropdown b-dropdown',
      class: this.dropdownClasses,
      attrs: {
        id: this.safeId()
      }
    }, [$split, $toggle, $menu]);
  }
});