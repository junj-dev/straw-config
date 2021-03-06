import Vue from '../../utils/vue';
import { BTd } from './td'; // TODO:
//   In Bootstrap v5, we won't need "sniffing" as table element variants properly inherit
//   to the child elements, so this can be converted to a functional component
// @vue/component

export var BTh = /*#__PURE__*/Vue.extend({
  name: 'BTh',
  extends: BTd,
  computed: {
    tag: function tag() {
      return 'th';
    }
  }
});