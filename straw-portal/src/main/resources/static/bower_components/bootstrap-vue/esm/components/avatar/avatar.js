function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); if (enumerableOnly) symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; }); keys.push.apply(keys, symbols); } return keys; }

function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i] != null ? arguments[i] : {}; if (i % 2) { ownKeys(Object(source), true).forEach(function (key) { _defineProperty(target, key, source[key]); }); } else if (Object.getOwnPropertyDescriptors) { Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)); } else { ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } } return target; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

import Vue from '../../utils/vue';
import { getComponentConfig } from '../../utils/config';
import { isNumber, isString, isUndefinedOrNull } from '../../utils/inspect';
import { toFloat } from '../../utils/number';
import { omit } from '../../utils/object';
import { pluckProps } from '../../utils/props';
import { isLink } from '../../utils/router';
import { BButton } from '../button/button';
import { BLink, props as BLinkProps } from '../link/link';
import { BIcon } from '../../icons/icon';
import { BIconPersonFill } from '../../icons/icons';
import normalizeSlotMixin from '../../mixins/normalize-slot'; // --- Constants ---

var NAME = 'BAvatar';
var CLASS_NAME = 'b-avatar';
var RX_NUMBER = /^[0-9]*\.?[0-9]+$/;
var FONT_SIZE_SCALE = 0.4;
var BADGE_FONT_SIZE_SCALE = FONT_SIZE_SCALE * 0.7;
var DEFAULT_SIZES = {
  sm: '1.5em',
  md: '2.5em',
  lg: '3.5em'
}; // --- Props ---

var linkProps = omit(BLinkProps, ['active', 'event', 'routerTag']);

var props = _objectSpread(_objectSpread({
  src: {
    type: String // default: null

  },
  text: {
    type: String // default: null

  },
  icon: {
    type: String // default: null

  },
  alt: {
    type: String,
    default: 'avatar'
  },
  variant: {
    type: String,
    default: function _default() {
      return getComponentConfig(NAME, 'variant');
    }
  },
  size: {
    type: [Number, String],
    default: null
  },
  square: {
    type: Boolean,
    default: false
  },
  rounded: {
    type: [Boolean, String],
    default: false
  },
  button: {
    type: Boolean,
    default: false
  },
  buttonType: {
    type: String,
    default: 'button'
  },
  badge: {
    type: [Boolean, String],
    default: false
  },
  badgeVariant: {
    type: String,
    default: function _default() {
      return getComponentConfig(NAME, 'badgeVariant');
    }
  },
  badgeTop: {
    type: Boolean,
    default: false
  },
  badgeLeft: {
    type: Boolean,
    default: false
  },
  badgeOffset: {
    type: String,
    default: '0px'
  }
}, linkProps), {}, {
  ariaLabel: {
    type: String // default: null

  }
}); // --- Utility methods ---


export var computeSize = function computeSize(value) {
  // Default to `md` size when `null`, or parse to
  // number when value is a float-like string
  value = isUndefinedOrNull(value) || value === '' ? 'md' : isString(value) && RX_NUMBER.test(value) ? toFloat(value, 0) : value; // Convert all numbers to pixel values
  // Handle default sizes when `sm`, `md` or `lg`
  // Or use value as is

  return isNumber(value) ? "".concat(value, "px") : DEFAULT_SIZES[value] || value;
}; // --- Main component ---
// @vue/component

export var BAvatar = /*#__PURE__*/Vue.extend({
  name: NAME,
  mixins: [normalizeSlotMixin],
  inject: {
    bvAvatarGroup: {
      default: null
    }
  },
  props: props,
  data: function data() {
    return {
      localSrc: this.src || null
    };
  },
  computed: {
    computedSize: function computedSize() {
      // Always use the avatar group size
      return computeSize(this.bvAvatarGroup ? this.bvAvatarGroup.size : this.size);
    },
    computedVariant: function computedVariant() {
      // Prefer avatar-group variant if provided
      var avatarGroup = this.bvAvatarGroup;
      return avatarGroup && avatarGroup.variant ? avatarGroup.variant : this.variant;
    },
    computedRounded: function computedRounded() {
      var avatarGroup = this.bvAvatarGroup;
      var square = avatarGroup && avatarGroup.square ? true : this.square;
      var rounded = avatarGroup && avatarGroup.rounded ? avatarGroup.rounded : this.rounded;
      return square ? '0' : rounded === '' ? true : rounded || 'circle';
    },
    fontStyle: function fontStyle() {
      var fontSize = this.computedSize;
      fontSize = fontSize ? "calc(".concat(fontSize, " * ").concat(FONT_SIZE_SCALE, ")") : null;
      return fontSize ? {
        fontSize: fontSize
      } : {};
    },
    marginStyle: function marginStyle() {
      var avatarGroup = this.bvAvatarGroup;
      var overlapScale = avatarGroup ? avatarGroup.overlapScale : 0;
      var size = this.computedSize;
      var value = size && overlapScale ? "calc(".concat(size, " * -").concat(overlapScale, ")") : null;
      return value ? {
        marginLeft: value,
        marginRight: value
      } : {};
    },
    badgeStyle: function badgeStyle() {
      var size = this.computedSize,
          badgeTop = this.badgeTop,
          badgeLeft = this.badgeLeft,
          badgeOffset = this.badgeOffset;
      var offset = badgeOffset || '0px';
      return {
        fontSize: size ? "calc(".concat(size, " * ").concat(BADGE_FONT_SIZE_SCALE, " )") : null,
        top: badgeTop ? offset : null,
        bottom: badgeTop ? null : offset,
        left: badgeLeft ? offset : null,
        right: badgeLeft ? null : offset
      };
    }
  },
  watch: {
    src: function src(newSrc, oldSrc) {
      if (newSrc !== oldSrc) {
        this.localSrc = newSrc || null;
      }
    }
  },
  methods: {
    onImgError: function onImgError(evt) {
      this.localSrc = null;
      this.$emit('img-error', evt);
    },
    onClick: function onClick(evt) {
      this.$emit('click', evt);
    }
  },
  render: function render(h) {
    var _class2;

    var variant = this.computedVariant,
        disabled = this.disabled,
        rounded = this.computedRounded,
        icon = this.icon,
        src = this.localSrc,
        text = this.text,
        fontStyle = this.fontStyle,
        marginStyle = this.marginStyle,
        size = this.computedSize,
        button = this.button,
        type = this.buttonType,
        badge = this.badge,
        badgeVariant = this.badgeVariant,
        badgeStyle = this.badgeStyle;
    var link = !button && isLink(this);
    var tag = button ? BButton : link ? BLink : 'span';
    var alt = this.alt || null;
    var ariaLabel = this.ariaLabel || null;
    var $content = null;

    if (this.hasNormalizedSlot('default')) {
      // Default slot overrides props
      $content = h('span', {
        staticClass: 'b-avatar-custom'
      }, [this.normalizeSlot('default')]);
    } else if (src) {
      $content = h('img', {
        style: variant ? {} : {
          width: '100%',
          height: '100%'
        },
        attrs: {
          src: src,
          alt: alt
        },
        on: {
          error: this.onImgError
        }
      });
      $content = h('span', {
        staticClass: 'b-avatar-img'
      }, [$content]);
    } else if (icon) {
      $content = h(BIcon, {
        props: {
          icon: icon
        },
        attrs: {
          'aria-hidden': 'true',
          alt: alt
        }
      });
    } else if (text) {
      $content = h('span', {
        staticClass: 'b-avatar-text',
        style: fontStyle
      }, [h('span', text)]);
    } else {
      // Fallback default avatar content
      $content = h(BIconPersonFill, {
        attrs: {
          'aria-hidden': 'true',
          alt: alt
        }
      });
    }

    var $badge = h();
    var hasBadgeSlot = this.hasNormalizedSlot('badge');

    if (badge || badge === '' || hasBadgeSlot) {
      var badgeText = badge === true ? '' : badge;
      $badge = h('span', {
        staticClass: 'b-avatar-badge',
        class: _defineProperty({}, "badge-".concat(badgeVariant), !!badgeVariant),
        style: badgeStyle
      }, [hasBadgeSlot ? this.normalizeSlot('badge') : badgeText]);
    }

    var componentData = {
      staticClass: CLASS_NAME,
      class: (_class2 = {}, _defineProperty(_class2, "badge-".concat(variant), !button && variant), _defineProperty(_class2, "rounded", rounded === true), _defineProperty(_class2, "rounded-".concat(rounded), rounded && rounded !== true), _defineProperty(_class2, "disabled", disabled), _class2),
      style: _objectSpread({
        width: size,
        height: size
      }, marginStyle),
      attrs: {
        'aria-label': ariaLabel || null
      },
      props: button ? {
        variant: variant,
        disabled: disabled,
        type: type
      } : link ? pluckProps(linkProps, this) : {},
      on: button || link ? {
        click: this.onClick
      } : {}
    };
    return h(tag, componentData, [$content, $badge]);
  }
});