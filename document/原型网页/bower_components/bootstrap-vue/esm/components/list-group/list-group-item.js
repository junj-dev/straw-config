function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); if (enumerableOnly) symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; }); keys.push.apply(keys, symbols); } return keys; }

function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i] != null ? arguments[i] : {}; if (i % 2) { ownKeys(Object(source), true).forEach(function (key) { _defineProperty(target, key, source[key]); }); } else if (Object.getOwnPropertyDescriptors) { Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)); } else { ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } } return target; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

import { mergeData } from 'vue-functional-data-merge';
import Vue from '../../utils/vue';
import { arrayIncludes } from '../../utils/array';
import { getComponentConfig } from '../../utils/config';
import { isTag } from '../../utils/dom';
import { omit } from '../../utils/object';
import { pluckProps } from '../../utils/props';
import { isLink } from '../../utils/router';
import { BLink, props as BLinkProps } from '../link/link'; // --- Constants ---

var NAME = 'BListGroupItem';
var actionTags = ['a', 'router-link', 'button', 'b-link']; // --- Props ---

var linkProps = omit(BLinkProps, ['event', 'routerTag']);
delete linkProps.href.default;
delete linkProps.to.default;
export var props = _objectSpread({
  tag: {
    type: String,
    default: 'div'
  },
  action: {
    type: Boolean,
    default: null
  },
  button: {
    type: Boolean,
    default: null
  },
  variant: {
    type: String,
    default: function _default() {
      return getComponentConfig(NAME, 'variant');
    }
  }
}, linkProps); // --- Main component ---
// @vue/component

export var BListGroupItem = /*#__PURE__*/Vue.extend({
  name: NAME,
  functional: true,
  props: props,
  render: function render(h, _ref) {
    var _class;

    var props = _ref.props,
        data = _ref.data,
        children = _ref.children;
    var button = props.button,
        variant = props.variant,
        active = props.active,
        disabled = props.disabled;
    var link = isLink(props);
    var tag = button ? 'button' : !link ? props.tag : BLink;
    var action = !!(props.action || link || button || arrayIncludes(actionTags, props.tag));
    var attrs = {};
    var itemProps = {};

    if (isTag(tag, 'button')) {
      if (!data.attrs || !data.attrs.type) {
        // Add a type for button is one not provided in passed attributes
        attrs.type = 'button';
      }

      if (props.disabled) {
        // Set disabled attribute if button and disabled
        attrs.disabled = true;
      }
    } else {
      itemProps = pluckProps(linkProps, props);
    }

    return h(tag, mergeData(data, {
      attrs: attrs,
      props: itemProps,
      staticClass: 'list-group-item',
      class: (_class = {}, _defineProperty(_class, "list-group-item-".concat(variant), variant), _defineProperty(_class, 'list-group-item-action', action), _defineProperty(_class, "active", active), _defineProperty(_class, "disabled", disabled), _class)
    }), children);
  }
});